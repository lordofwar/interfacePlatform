package com.gxlu.interfacePlatform.client.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.lang.StringUtils;

import com.gxlu.interfacePlatform.database.hibernate.ScheduleDao;
import com.gxlu.interfacePlatform.database.hibernate.ScheduleDaoImpl;
import com.gxlu.interfacePlatform.schedule.DefaultScheduleManager;
import com.gxlu.interfacePlatform.schedule.Schedule;
import com.gxlu.interfacePlatform.schedule.ScheduleListener;
import com.gxlu.interfacePlatform.schedule.ScheduleListenerManager;
import com.gxlu.interfacePlatform.schedule.ScheduleLoader;
import com.gxlu.interfacePlatform.schedule.ScheduleManager;
import com.gxlu.interfacePlatform.schedule.plugins.AutoClassLoader;

public class MainFrameController {
	private MainFrame mainframe;
	private ScheduleManager scheduleManager;
	private ScheduleListenerManager scheduleListenerManager; 
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public MainFrameController(MainFrame mainframe){
		this.mainframe=mainframe;
		this.initial();
		refresh();
	}
	
	private void initial(){
		mainframe.getBtnStart().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 try {
					checkCode();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(mainframe, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				scheduleManager = new DefaultScheduleManager(new ScheduleLoader() {

					public List<Schedule> load() {
						// TODO Auto-generated method stub
						
						List<Schedule> list = getSchedules();
						return list;
					}
				});
				
				scheduleListenerManager=(ScheduleListenerManager)scheduleManager;
				scheduleListenerManager.addListener(new ScheduleListener() {
					
					public void beforeExecute(Schedule schedule) {
						// TODO Auto-generated method stub
						ScheduleDao dao = new ScheduleDaoImpl();
						schedule.setStatus(1);
						dao.modify(schedule);
						refresh();
					}
					
					public void afterExecute(Schedule schedule) {
						// TODO Auto-generated method stub
						ScheduleDao dao = new ScheduleDaoImpl();
						schedule.setStatus(0);
						dao.modify(schedule);
						refresh();
					}
				});
				scheduleManager.startCron();
				mainframe.getBtnStop().setEnabled(true);
				mainframe.getBtnStart().setEnabled(false);
			}
		});
		mainframe.getBtnStop().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(scheduleManager!=null){
					scheduleManager.stopCron();
					mainframe.getBtnStop().setEnabled(false);
					mainframe.getBtnStart().setEnabled(true);
				}
			}
		});
		
		
		mainframe.getBtnRefresh().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refresh();
			}
		});
		
		mainframe.getBtnAdd().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model=(DefaultTableModel)mainframe.getTable().getModel();
				model.addRow(new Object[]{});
			}
		});
		
		
		mainframe.getBtnDelete().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DefaultTableModel model=(DefaultTableModel)mainframe.getTable().getModel();
				int[] rows =mainframe.getTable().getSelectedRows();
				ScheduleDao dao = new ScheduleDaoImpl();
				try{
					for(int i=0;i<rows.length;i++){
						
						int row = rows[i];
						Object value=model.getValueAt(row, 0);
						if(value!=null && StringUtils.isNotBlank(value.toString())){
							Schedule s = new Schedule();
							s.setId((Integer)value);
							dao.delete(s);
						}
					}
				}finally{
					refresh();
				}
			}
		});
		
		mainframe.getBtnSave().addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				List<Schedule> list = getSchedules();
				ScheduleDao dao = new ScheduleDaoImpl();
				for(Schedule schedule :list){
					if(schedule.getId()!=null && StringUtils.isNotBlank(schedule.getId().toString()))
						dao.modify(schedule);
					else{
						dao.insert(schedule);
					}
				}
			}
		});
		
		JComboBox JComboBoxItem = new JComboBox(AutoClassLoader.loadScheduleHandlers());
		
        TableColumn  brandColumn = mainframe.getTable().getColumnModel().getColumn(getColumnIndex("MetaCategory"));
        brandColumn.setCellEditor(new DefaultCellEditor(JComboBoxItem));
        
        JCheckBox checkbox = new JCheckBox();
        TableColumn  isActiveColumn = mainframe.getTable().getColumnModel().getColumn(getColumnIndex("isActive"));
        isActiveColumn.setCellEditor(new DefaultCellEditor(checkbox));
        
	}
	
	private int getColumnIndex(String columnName){
		
		for(int i=0;i<mainframe.getTable().getColumnCount();i++){
			if(mainframe.getTable().getColumnName(i).equals(columnName)){
				return i;
			}
		}
		return 0;
	}
	
	private List<Schedule> getSchedules(){
		List<Schedule> list = new LinkedList<Schedule>();
		DefaultTableModel model=(DefaultTableModel)mainframe.getTable().getModel();
		for(int rowIndex=0;rowIndex<model.getRowCount();rowIndex++){
			Schedule schedule = new Schedule();
			Object id =model.getValueAt(rowIndex, getColumnIndex("ID"));
			if(id!=null){
				schedule.setId(Integer.parseInt(id.toString()));
			}
			schedule.setName((String)model.getValueAt(rowIndex, getColumnIndex("Name")));
			schedule.setCode((String)model.getValueAt(rowIndex, getColumnIndex("Code")));
			schedule.setHandlerClassName((String)model.getValueAt(rowIndex, getColumnIndex("MetaCategory")));
			String startTime =(String)model.getValueAt(rowIndex, getColumnIndex("StartTime"));
			if(StringUtils.isNotBlank(startTime)){
				try {
					schedule.setStartTime(format.parse(startTime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Object interval =model.getValueAt(rowIndex, getColumnIndex("Interval"));
			if(interval!=null&&StringUtils.isNotBlank(interval.toString()))
				schedule.setInterval(Integer.parseInt(interval.toString()));
			Object status =model.getValueAt(rowIndex, getColumnIndex("Status"));
			if(status!=null&&StringUtils.isNotBlank(status.toString()))
				schedule.setStatus(Integer.parseInt(status.toString()));
			schedule.setComments((String)model.getValueAt(rowIndex, getColumnIndex("Comment")));
			Object isActive =model.getValueAt(rowIndex, getColumnIndex("isActive"));
			
			if(isActive!=null)
				schedule.setActive((Boolean)isActive);
			
			list.add(schedule);
		}
		return list;
	}
	
	private void checkCode() throws Exception{
		List<String> codes = new LinkedList<String>();
		
		DefaultTableModel model=(DefaultTableModel)mainframe.getTable().getModel();
		for(int rowIndex=0;rowIndex<model.getRowCount();rowIndex++){
			Object code =model.getValueAt(rowIndex, getColumnIndex("Code"));
			if(code==null || StringUtils.isBlank(code.toString())){
				throw new Exception("Code 不能为空！");
			}
			if(codes.contains(code.toString())){
				throw new Exception("Code 必须唯一！");
			}
			codes.add(code.toString());
		}
	}
	
	private void refresh(){
		ScheduleDao dao = new ScheduleDaoImpl();
		final List<Schedule> result = dao.queryAll();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				DefaultTableModel model=(DefaultTableModel)mainframe.getTable().getModel();
				model.getDataVector().clear();
				for(Schedule schedule :result){
					
					String startTime ="";
					if(schedule.getStartTime()!=null)
						startTime =format.format(schedule.getStartTime());
					
					model.addRow(new Object[]{schedule.getId(),schedule.getName(),schedule.getCode(),schedule.getHandlerClassName(),startTime,schedule.getInterval(),schedule.getComments(),schedule.getStatus(),schedule.isActive()});
				}
				
				
			}
		});
		
	}
}
