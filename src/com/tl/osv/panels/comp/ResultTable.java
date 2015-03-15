/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tl.osv.panels.comp;

import com.tl.osv.Log;
import com.tl.osv.Mls;
import com.tl.osv.dev.ProbMeasuringSet;
import com.tl.osv.dev.MeasuringResult;
import com.tl.osv.dev.MeasuringResultRecord;
import com.tl.osv.dev.ProbMeasuring;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author root
 */
public class ResultTable extends javax.swing.JPanel {
    JTable dataTable;
    JTable elementTable;
    ResultTableListener selectorListener;
    /**
     * Creates new form ResultTable
     */
    public ResultTable() {
        initComponents();
        dataTable = new JTable();
        dataTable.setModel(new DefaultTableModel(5, 5));
        dataTable.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = dataTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                checkDataTableSelection();
            }
        });
        
        dataTable.getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                checkDataTableSelection();
            }
        });
        
        elementTable = new JTable();
        elementTable.setModel(new DefaultTableModel(5, 1));
        elementTable.setCellSelectionEnabled(false);
        elementTable.setRowSelectionAllowed(false);
        
        scrollPaneMain.setCorner(JScrollPane.UPPER_LEFT_CORNER, 
                new JLabel(Mls.translate("#Probs")));
        scrollPaneMain.setRowHeaderView(elementTable);
        scrollPaneMain.setViewportView(dataTable);
    }

    public void checkDataTableSelection(){
        try{
            if(selectorListener == null)
                return;
            int row = dataTable.getSelectedRow();
            int col = dataTable.getSelectedColumn();
            ProbMeasuringSet probSet;
            ProbMeasuring probMeasuring;
            if(col >= 0){
                probSet = probs.get(col);
                probMeasuring = probMeasurings.get(col);
            } else {
                probSet = null;
                probMeasuring = null;
            }
            selectorListener.selectProb(probSet, probMeasuring, 
                    row, elementList);
        }catch(Exception ex){
            Log.exception(ex);
        }
    }
    public void setListener(ResultTableListener selectorListener){
        this.selectorListener = selectorListener;
    }
    
    private int addElementList() throws Exception{
        Comparator comp = (Comparator) (Object o1, Object o2) -> {
            String str1 = (String)o1;
            String str2 = (String)o2;
            return str1.compareTo(str2);
        };
        TreeSet<String> elements = new TreeSet<>(comp);
        for(ProbMeasuring measuring : probMeasurings){
            if(measuring == null)
                continue;
            MeasuringResult mr = measuring.getResult();
            if(mr == null)
                continue;
            for(int e = 0;e<mr.size();e++){
                MeasuringResultRecord record = mr.get(e);
                if(record.elementName != null)
                    elements.add(record.elementName);
            }
        }
        String[][] elTableData;
        if(elements.size() > 0){
            Object[] elementListTmp = elements.toArray();
            elementList = new String[elementListTmp.length];
            elTableData = new String[elementListTmp.length+1][1];
            for(int i = 0;i<elementList.length;i++){
                elementList[i] = (String)elementListTmp[i];
                elTableData[i][0] = elementList[i];
            }
            elTableData[elementListTmp.length] = new String[1];
            elTableData[elementListTmp.length][0] = Mls.translate("#Date");
        } else {
            elTableData = new String[1][1];
            elTableData[0][0] = Mls.translate("#Date");
        }
        DefaultTableModel def = new DefaultTableModel(elTableData, 
                    new String[]{"Elements"});
        elementTable.setModel(def);
        elementTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int width = elementTable.getColumnModel().getColumn(0).getPreferredWidth();
        elementTable.setPreferredScrollableViewportSize(new Dimension(width,20));
        
        return elTableData.length;
    }
    
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd hh:mm");
    private File folderView; 
    private ArrayList<ProbMeasuringSet> probs = new ArrayList<>();
    private ArrayList<ProbMeasuring> probMeasurings = new ArrayList<>();    
    private String[] elementList;
    public void updateView() throws Exception{
        File[] probList = folderView.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory() && pathname.getName().endsWith(ProbMeasuringSet.PROB_EXTENTION);
            }
        });
        
        probs.clear();
        probMeasurings.clear();
        for(int p = 0;p < probList.length;p++){
            ProbMeasuringSet set = ProbMeasuringSet.load(probList[p]);
            probs.add(set);
            probMeasurings.add(null);
            for(int m = 0;m<set.size();m++){
                ProbMeasuring pm = set.get(m);
                probs.add(set);
                probMeasurings.add(pm);
            }
        }
        
        int elCount = addElementList();
        
        Object[][] data = new Object[elCount][probs.size()];
        Object[] colName = new Object[probs.size()];
        for(int i = 0;i<probs.size();i++){
            ProbMeasuringSet set = probs.get(i);
            ProbMeasuring measuring = probMeasurings.get(i);
            if(measuring != null)
                colName[i] = "_";
            else
                colName[i] = set.getName();
            for(int e = 0;e<elCount-1;e++){
                data[e][i] = "e"+e+"c"+i;
            }
            if(measuring != null){
                String name = measuring.getName();
                int index = name.indexOf(".");
                if(index > 0)
                    name = name.substring(0,index);
                Date date = new Date(Long.parseLong(name));
                data[elCount-1][i] = DATE_FORMAT.format(date);
            }
        }
        DefaultTableModel model = new DefaultTableModel(data,colName);
        dataTable.setModel(model);
        dataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(dataTable);
        dataTable.setCellSelectionEnabled(true);
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    public ProbMeasuringSet getSelectedMeasuringProb(){
        int selectedCol = dataTable.getSelectedColumn();
        if(selectedCol < 0)
            return null;
        return probs.get(selectedCol);
    }
    
    public ProbMeasuring getSelectedMeasuringProbSpectr(){
        int selectedCol = dataTable.getSelectedColumn();
        if(selectedCol < 0)
            return null;
        return probMeasurings.get(selectedCol);
    }
    
    public int getSelectedElement(){
        return dataTable.getSelectedRow();
    }
    
    public void setupView(File folderView) throws Exception{
        this.folderView = folderView;
        updateView();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneMain = new javax.swing.JScrollPane();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPaneMain;
    // End of variables declaration//GEN-END:variables

}
