package bsearch.temp;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import bsearch.fx.AcceptOnExitTableCell;
import bsearch.fx.DataCollectionTableRow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
//	private ObservableList<DataCollectionTableRow> list;
	LinkedHashMap<String, String> map;
	private int nextTableVariableNum;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//list = FXCollections.observableArrayList(new ArrayList<DataCollectionTableRow>());
		nextTableVariableNum = 0;
		map = new LinkedHashMap<String, String>();
		//TODO configDataCollectionTable(tab,varCol,codeCol)
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
//		table.setEditable(true);
		dc_varCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		dc_varCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("variable"));
		dc_codeCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		dc_codeCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("code"));
	}
	
	public void handleAddMeasureButton() {
		
		DataCollectionTableRow newRow = new DataCollectionTableRow("RAW" + nextTableVariableNum, "mean [energy] of turtles");
		table.getItems().add(newRow);
		map.put(newRow.getVariable(), newRow.getCode());
		
		//table.setItems(list);
		nextTableVariableNum++;
		/*for(int j = 0; j < map.size(); j++) {
			String key = list.get(j).getVariable();
			System.out.println(key + " " + map.get(key));
		}*/
	}
	
	public void handleRemoveMeasureButton() {
		table.getItems().remove(table.getSelectionModel().getSelectedItem());
		map.remove(table.getSelectionModel().getSelectedItem().getVariable());
	}
}
