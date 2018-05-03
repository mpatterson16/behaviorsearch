package bsearch.fx;

import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;

public class DataCollectionTableUtils {

	public static void configTableEditing(TableView<DataCollectionTableRow> table, 
			TableColumn<DataCollectionTableRow, String> varCol, 
			TableColumn<DataCollectionTableRow, String> codeCol) {
	
		EventHandler<TableColumn.CellEditEvent<DataCollectionTableRow,String>> varCellEditHandler;
		varCellEditHandler = new EventHandler<TableColumn.CellEditEvent<DataCollectionTableRow,String>>() {
			private String originalOldValue;
			@Override
			public void handle(CellEditEvent<DataCollectionTableRow, String> e) {
				if (e.getNewValue() == null) { // this is a EditStart event!
					originalOldValue = e.getOldValue();
				} else {  // it's an EditCommit event
					if (e.getOldValue() == null || !e.getOldValue().equals(originalOldValue)) {
						return; // someone deleted the row while editing it (or something else weird)
					} else if (e.getNewValue().equals(e.getOldValue())) {
						return; // the user didn't change the name at all
					}
					
					Set<String> existingKeys = table.getItems().stream().map(row -> row.getVariable()).collect(Collectors.toSet());
					
					// check if it's a valid variable name (and not used already)
					if(!existingKeys.contains(e.getNewValue()) && !e.getNewValue().trim().equals("")) {
						e.getRowValue().setVariable(e.getNewValue());
					} else {
						e.getTableView().getItems().get(e.getTablePosition().getRow()).setVariable(e.getOldValue());
						table.refresh();
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Improper Variable");
						alert.setContentText("You cannot have two variables with the same name or a variable without a name.");
						alert.showAndWait();						
					}
				}
				
			}
		};
		varCol.setOnEditStart(varCellEditHandler);
		varCol.setOnEditCommit(varCellEditHandler);
		varCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		varCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("variable"));

		EventHandler<TableColumn.CellEditEvent<DataCollectionTableRow,String>> codeCellEditHandler;
		codeCellEditHandler = new EventHandler<TableColumn.CellEditEvent<DataCollectionTableRow,String>>() {
			private String originalOldValue;
			@Override
			public void handle(CellEditEvent<DataCollectionTableRow, String> e) {
				if (e.getNewValue() == null) { // this is a EditStart event!
					originalOldValue = e.getOldValue();
				} else {  // it's an EditCommit event
					// make sure no one deleted the row while editing it (or something else weird)
					if (e.getOldValue() != null && e.getOldValue().equals(originalOldValue)) {
						e.getRowValue().setCode(e.getNewValue());
					}
				}
			}
		};
		codeCol.setOnEditStart(codeCellEditHandler);
		codeCol.setOnEditCommit(codeCellEditHandler);
		codeCol.setCellFactory(AcceptOnExitTableCell.forTableColumn());
		codeCol.setCellValueFactory(new PropertyValueFactory<DataCollectionTableRow, String>("code"));
	}

}
