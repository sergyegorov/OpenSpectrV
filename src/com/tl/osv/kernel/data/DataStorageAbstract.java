/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.kernel.data;

import com.tl.osv.Mls;
import com.tl.osv.kernel.data.params.AbstractParameter;
import com.tl.osv.kernel.data.params.IntParameter;
import com.tl.osv.util.StreamTools;
import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author root
 */
public abstract class DataStorageAbstract {
    protected DataStorageAbstract parent;
    String name,description;
    protected int version;
    protected DataStorageAbstract(String name,String description){
        this.name = name;
        this.description = description;
        this.version = -1;
    }
    
    public void putValues(JPanel panel){
        //jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("tttt"));
        panel.setLayout(new BorderLayout());
        for(int i = 0;i<this.getFolderSize();i++){
            DataStorageAbstract folder = this.getFolder(i);
            JPanel folderPanel = new JPanel(new BorderLayout());
            folderPanel.setBorder(BorderFactory.createTitledBorder(Mls.translate(folder.getName())));
            folder.putValues(folderPanel);
            panel.add(folderPanel,BorderLayout.NORTH);
            JPanel newPanel = new JPanel(new BorderLayout());
            panel.add(newPanel,BorderLayout.CENTER);
            panel = newPanel;
        }
        for(int i = 0;i<this.getParameterSize();i++){
            AbstractParameter ap = this.getParameter(i);
            JComponent comp = ap.getGui();
            panel.add(comp,BorderLayout.NORTH);
            JPanel newPanel = new JPanel(new BorderLayout());
            panel.add(newPanel,BorderLayout.CENTER);
            panel = newPanel;
        }
    }
    
    public int getVersion(){
        return this.version;
    }
    
    public void setVersion(int version){
        this.version = version;
    }
    
    final public void commit() throws Exception{
        getRoot().commitInternal();
    }
    abstract protected void commitInternal() throws Exception;
    //abstract public DataStorageAbstract createInstance() throws Exception ;
    
    public String getName(){
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    private void setParent(DataStorageAbstract parent){
        this.parent = parent;        
    }
    
    private ArrayList<AbstractParameter> parameterList = new ArrayList<>();
    public void add(AbstractParameter parameter){
        parameter.setParent(this);
        parameterList.add(parameter);
    }
    public AbstractParameter findParameterByName(String name,boolean withAllSubFolders){
        for (AbstractParameter parameter : parameterList) {
            if(parameter.getName().equals(name))
                return parameter;
        }
        if(withAllSubFolders == false)
            return null;
        for(DataStorageAbstract folder : folderList){
            AbstractParameter found = folder.findParameterByName(name, true);
            if(found != null)
                return found;
        }
        return null;
    }
    public AbstractParameter getParameter(int index){
        return parameterList.get(index);
    }
    public void removeParameter(int index){
        parameterList.remove(index).setParent(null);
    }
    public int getParameterSize(){
        return parameterList.size();
    }
    
    final private ArrayList<DataStorageAbstract> folderList = new ArrayList<>();
    public void add(DataStorageAbstract folder){
        folder.setParent(this);
        folderList.add(folder);
    }
    public int findFolderByName(String name){
        for(int i = 0;i<folderList.size();i++){
            DataStorageAbstract parameter = folderList.get(i);
            if(parameter.name.equals(name))
                return i;
        }
        return -1;
    }
    public DataStorageAbstract getFolder(int index){
        return folderList.get(index);
    }
    public void removeFolder(int index){
        folderList.remove(index).setParent(null);
    }
    public int getFolderSize(){
        return folderList.size();
    }
    public DataStorageAbstract getRoot(){
        if(parent != null)
            return parent.getRoot();
        return this;
    }
    
    protected void load(DataInputStream stream) throws Exception{
        StreamTools.versionBlockBegin(stream, 1, 1);
        
        version = stream.readInt();
        
        name = StreamTools.readString(stream);
        description = StreamTools.readString(stream);
        
        int n = stream.readInt();
        for(int i = 0;i<n;i++){
            String className = StreamTools.readString(stream);
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor();//String.class);
            DataStorageAbstract folder = (DataStorageAbstract)ctor.newInstance();
            folder.load(stream);
            add(folder);
        }
        
        n = stream.readInt();
        for(int i = 0;i<n;i++){
            String className = StreamTools.readString(stream);
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor();//String.class);
            AbstractParameter parameter = (AbstractParameter)ctor.newInstance();//new Object[] { ctorArgument });
            parameter.load(stream);
            add(parameter);
        }
        
        StreamTools.versionBlockEnd(stream);
    }
    
    protected void save(DataOutputStream stream) throws Exception{
        int folderCount = getFolderSize();
        StreamTools.versionBlockBegin(stream, 1);
        
        stream.writeInt(version);
        
        StreamTools.writeString(name, stream);
        StreamTools.writeString(description, stream);
        
        stream.writeInt(folderCount);
        for(int fi = 0;fi<folderCount;fi++){
            DataStorageAbstract folder = getFolder(fi);
            StreamTools.writeString(folder.getClass().getName(), stream);
            folder.save(stream);
        }
        
        int parameterCount = getParameterSize();
        stream.writeInt(parameterCount);
        for(int i = 0;i<parameterCount;i++){
            AbstractParameter parameter = getParameter(i);
            StreamTools.writeString(parameter.getClass().getName(), stream);
            parameter.save(stream);
        }
        
        StreamTools.versionBlockEnd(stream);
    }
    
    public int findAsInteger(String name){
        AbstractParameter param = findParameterByName(name, true);
        return ((IntParameter)param).getAsInteger();
    }
}
