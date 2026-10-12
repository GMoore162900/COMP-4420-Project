package edu.westga.comp4420.it_asset_management.view;

import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

/**
 * Controller for the inventory management dashboard.
 *
 * @author Comp 4420
 * @version Fall 2026
 */
public class ManagementWindowController {
    private static final String ALL_FILTER_OPTION = "All";

    @FXML
    private ComboBox<String> deviceTypeFilter;

    @FXML
    private ComboBox<String> userTypeFilter;

    @FXML
    private ComboBox<String> departmentFilter;

    @FXML
    private ComboBox<String> buildingFilter;

    @FXML
    private TableView<DeviceRecord> deviceTable;

    @FXML
    private TableColumn<DeviceRecord, String> deviceNameColumn;

    @FXML
    private TableColumn<DeviceRecord, String> deviceTypeColumn;

    @FXML
    private TableColumn<DeviceRecord, String> userTypeColumn;

    @FXML
    private TableColumn<DeviceRecord, String> departmentColumn;

    @FXML
    private TableColumn<DeviceRecord, String> buildingColumn;

    @FXML
    private TableColumn<DeviceRecord, String> statusColumn;

    @FXML
    private Label actionStatusLabel;

    private final ObservableList<DeviceRecord> allDevices = FXCollections.observableArrayList();
    private final FilteredList<DeviceRecord> filteredDevices = new FilteredList<>(this.allDevices, device -> true);

    /**
     * Initialize the dashboard table and filters.
     */
    @FXML
    private void initialize() {
        this.configureColumns();
        this.configureFilters();
        this.loadSampleData();
        this.deviceTable.setItems(this.filteredDevices);
    }

    @FXML
    private void onApplyFilters() {
        this.filteredDevices.setPredicate(this::matchesActiveFilters);
        this.actionStatusLabel.setText("Filters applied.");
    }

    @FXML
    private void onAddDevice() {
        Optional<DeviceRecord> newDevice = this.showDeviceDialog("Add New Device", null);
        if (newDevice.isEmpty()) {
            this.actionStatusLabel.setText("Add device cancelled.");
            return;
        }

        this.allDevices.add(newDevice.get());
        this.onApplyFilters();
        this.actionStatusLabel.setText("New device added successfully.");
    }

    @FXML
    private void onEditSelectedDevice() {
        DeviceRecord selectedDevice = this.deviceTable.getSelectionModel().getSelectedItem();
        if (selectedDevice == null) {
            this.actionStatusLabel.setText("Select a device to edit.");
            return;
        }

        Optional<DeviceRecord> editedDevice = this.showDeviceDialog("Edit Device", selectedDevice);
        if (editedDevice.isEmpty()) {
            this.actionStatusLabel.setText("Edit device cancelled.");
            return;
        }

        this.replaceDevice(selectedDevice, editedDevice.get());
        this.onApplyFilters();
        this.actionStatusLabel.setText("Device details updated.");
    }

    private void configureColumns() {
        this.deviceNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        this.deviceTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDeviceType()));
        this.userTypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUserType()));
        this.departmentColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDepartment()));
        this.buildingColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBuilding()));
        this.statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus()));
    }

    private void configureFilters() {
        this.deviceTypeFilter.setItems(FXCollections.observableArrayList(
                ALL_FILTER_OPTION, "Laptop", "Desktop", "TV", "Smart Board", "Tablet", "Networking Device"));
        this.userTypeFilter.setItems(FXCollections.observableArrayList(
                ALL_FILTER_OPTION, "Student", "Staff", "Admin"));
        this.departmentFilter.setItems(FXCollections.observableArrayList(
                ALL_FILTER_OPTION, "IT Services", "Computer Science", "Business", "Biology", "Fine Arts", "Facilities"));
        this.buildingFilter.setItems(FXCollections.observableArrayList(
                ALL_FILTER_OPTION, "Adamson Hall", "Education Center", "Library", "Nursing Building", "Tech Annex"));

        this.deviceTypeFilter.setValue(ALL_FILTER_OPTION);
        this.userTypeFilter.setValue(ALL_FILTER_OPTION);
        this.departmentFilter.setValue(ALL_FILTER_OPTION);
        this.buildingFilter.setValue(ALL_FILTER_OPTION);
    }

    private void loadSampleData() {
        this.allDevices.add(new DeviceRecord("Lenovo ThinkPad X1", "Laptop", "Staff", "IT Services", "Tech Annex", "In Service"));
        this.allDevices.add(new DeviceRecord("Dell Latitude 5540", "Laptop", "Student", "Computer Science", "Adamson Hall", "In Service"));
        this.allDevices.add(new DeviceRecord("HP EliteDesk 800", "Desktop", "Staff", "Business", "Education Center", "In Service"));
        this.allDevices.add(new DeviceRecord("Samsung UHD 65", "TV", "Admin", "Facilities", "Library", "In Service"));
        this.allDevices.add(new DeviceRecord("Promethean Board", "Smart Board", "Staff", "Biology", "Nursing Building", "Needs Service"));
        this.allDevices.add(new DeviceRecord("iPad 10th Gen", "Tablet", "Student", "Fine Arts", "Adamson Hall", "In Service"));
        this.allDevices.add(new DeviceRecord("Cisco Catalyst 9200", "Networking Device", "Admin", "Computer Science", "Tech Annex", "In Service"));
    }

    private boolean matchesActiveFilters(DeviceRecord device) {
        return this.matchesFilterValue(this.deviceTypeFilter.getValue(), device.getDeviceType())
                && this.matchesFilterValue(this.userTypeFilter.getValue(), device.getUserType())
                && this.matchesFilterValue(this.departmentFilter.getValue(), device.getDepartment())
                && this.matchesFilterValue(this.buildingFilter.getValue(), device.getBuilding());
    }

    private boolean matchesFilterValue(String selectedValue, String deviceValue) {
        return ALL_FILTER_OPTION.equals(selectedValue) || selectedValue.equals(deviceValue);
    }

    private Optional<DeviceRecord> showDeviceDialog(String title, DeviceRecord existingDevice) {
        String defaultName = this.getDefault(existingDevice, FieldType.NAME);
        String defaultType = this.getDefault(existingDevice, FieldType.DEVICE_TYPE);
        String defaultUserType = this.getDefault(existingDevice, FieldType.USER_TYPE);
        String defaultDepartment = this.getDefault(existingDevice, FieldType.DEPARTMENT);
        String defaultBuilding = this.getDefault(existingDevice, FieldType.BUILDING);
        String defaultStatus = this.getDefault(existingDevice, FieldType.STATUS);

        Optional<String> name = this.prompt(title, "Device Name", defaultName);
        Optional<String> type = this.prompt(title, "Device Type", defaultType);
        Optional<String> userType = this.prompt(title, "User Type", defaultUserType);
        Optional<String> department = this.prompt(title, "Department", defaultDepartment);
        Optional<String> building = this.prompt(title, "Building", defaultBuilding);
        Optional<String> status = this.prompt(title, "Status", defaultStatus);

        if (name.isEmpty() || type.isEmpty() || userType.isEmpty() || department.isEmpty() || building.isEmpty() || status.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DeviceRecord(name.get(), type.get(), userType.get(), department.get(), building.get(), status.get()));
    }

    private Optional<String> prompt(String title, String fieldName, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(fieldName);
        dialog.setContentText("Value:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return Optional.empty();
        }

        String trimmedValue = result.get().trim();
        if (trimmedValue.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(trimmedValue);
    }

    private String getDefault(DeviceRecord existingDevice, FieldType fieldType) {
        if (existingDevice == null) {
            return "";
        }
        switch (fieldType) {
            case NAME:
                return existingDevice.getName();
            case DEVICE_TYPE:
                return existingDevice.getDeviceType();
            case USER_TYPE:
                return existingDevice.getUserType();
            case DEPARTMENT:
                return existingDevice.getDepartment();
            case BUILDING:
                return existingDevice.getBuilding();
            default:
                return existingDevice.getStatus();
        }
    }

    private void replaceDevice(DeviceRecord oldDevice, DeviceRecord updatedDevice) {
        int index = this.allDevices.indexOf(oldDevice);
        if (index >= 0) {
            this.allDevices.set(index, updatedDevice);
        }
    }

    private enum FieldType {
        NAME,
        DEVICE_TYPE,
        USER_TYPE,
        DEPARTMENT,
        BUILDING,
        STATUS
    }

    private static final class DeviceRecord {
        private final String name;
        private final String deviceType;
        private final String userType;
        private final String department;
        private final String building;
        private final String status;

        private DeviceRecord(String name, String deviceType, String userType,
                String department, String building, String status) {
            this.name = name;
            this.deviceType = deviceType;
            this.userType = userType;
            this.department = department;
            this.building = building;
            this.status = status;
        }

        private String getName() {
            return this.name;
        }

        private String getDeviceType() {
            return this.deviceType;
        }

        private String getUserType() {
            return this.userType;
        }

        private String getDepartment() {
            return this.department;
        }

        private String getBuilding() {
            return this.building;
        }

        private String getStatus() {
            return this.status;
        }
    }
}
