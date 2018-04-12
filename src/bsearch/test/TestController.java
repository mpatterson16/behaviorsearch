package bsearch.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import bsearch.fx.AcceptOnExitTableCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;

public class TestController implements Initializable {
	@FXML
	private TableColumn<DataCollectionTableRow, String> dc_varCol;
	@FXML
	private TableColumn<DataCollectionTableRow, String> dc_codeCol;
	@FXML
	private TableView<DataCollectionTableRow> table;
	@FXML
	private Button dc_addMeasureButton;
	@FXML
	private Button dc_removeMeasureButton;
	
	private ArrayList<DataCollectionTableRow> list;
	LinkedHashMap<String, String> map;
	private int num;
	private int ind;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list = new ArrayList<DataCollectionTableRow>();
		num = 0;
		ind = 0;
		map = new LinkedHashMap<String, String>();
		dc_varCol.setOnEditCommit(new EventHandler<CellEditEvent<DataCollectionTableRow, String>>() {
			@Override
			public void handle(CellEditEvent<DataCollectionTableRow, String> e) {
				String val = map.remove(e.getOldValue());
				map.put(e.getNewValue(), val);
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setVariable(e.getNewValue());
			}
		});
		dc_codeCol.setOnEditCommit(new EventHandler<CellEditEvent<DataCollectionTableRow, String>>() {
			@Override
			public void handle(CellEditEvent<DataCollectionTableRow, String> e) {
				map.replace(e.getTableView().getItems().get(e.getTablePosition().getRow()).getVariable(), e.getOldValue(), e.getNewValue());
				e.getTableView().getItems().get(e.getTablePosition().getRow()).setCode(e.getNewValue());
			}
		});
		
	}
	
	public void handleAddMeasureButton() {
		table.setEditable(true);
		list.add(new DataCollectionTableRow("RAW" + num, "mean [energy] of turtles"));
		dc_varCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		dc_varCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("variable"));
		dc_codeCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		dc_codeCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("code"));
		table.setItems(FXCollections.observableArrayList(list));
		map.put(list.get(ind).getVariable(), list.get(ind).getCode());
		num++;
		ind++;
		/*for(int j = 0; j < map.size(); j++) {
			String key = list.get(j).getVariable();
			System.out.println(key + " " + map.get(key));
		}*/
	}
	
	public void handleRemoveMeasureButton() {
		list.remove(table.getSelectionModel().getSelectedItem());
		map.remove(table.getSelectionModel().getSelectedItem().getVariable());
		table.setItems(FXCollections.observableArrayList(list));
		ind--;
	}
}
