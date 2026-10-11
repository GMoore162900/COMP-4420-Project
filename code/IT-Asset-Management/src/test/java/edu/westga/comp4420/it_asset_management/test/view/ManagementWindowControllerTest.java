package edu.westga.comp4420.it_asset_management.test.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

class ManagementWindowControllerTest {
    private static final String MANAGEMENT_RESOURCE =
            "/edu/westga/comp4420/it_asset_management/view/Management.fxml";
    private static final int TIMEOUT_SECONDS = 5;

    @BeforeAll
    static void setUpToolkit() throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException exception) {
            latch.countDown();
        }

        if (!latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            failure.set(new IllegalStateException("JavaFX toolkit did not start."));
        }
        if (failure.get() != null) {
            throw new Exception(failure.get());
        }
    }

    @Test
    void testInitializeLoadsSampleDevicesAndFilterOptions() throws Exception {
        Object controller = this.loadController();
        TableView<?> deviceTable = this.getFieldValue(controller, "deviceTable", TableView.class);
        ComboBox<?> deviceTypeFilter = this.getFieldValue(controller, "deviceTypeFilter", ComboBox.class);
        ComboBox<?> userTypeFilter = this.getFieldValue(controller, "userTypeFilter", ComboBox.class);

        assertEquals(7, deviceTable.getItems().size());
        assertTrue(deviceTypeFilter.getItems().contains("Laptop"));
        assertTrue(userTypeFilter.getItems().contains("Admin"));
    }

    @Test
    void testApplyFiltersNarrowsVisibleDevices() throws Exception {
        Object controller = this.loadController();
        ComboBox<String> deviceTypeFilter = this.getFieldValue(controller, "deviceTypeFilter", ComboBox.class);
        ComboBox<String> departmentFilter = this.getFieldValue(controller, "departmentFilter", ComboBox.class);
        TableView<?> deviceTable = this.getFieldValue(controller, "deviceTable", TableView.class);

        this.runOnFxThreadAndWait(() -> {
            deviceTypeFilter.setValue("Laptop");
            departmentFilter.setValue("IT Services");
            this.invokeMethod(controller, "onApplyFilters");
        });

        assertEquals(1, deviceTable.getItems().size());
        Object device = deviceTable.getItems().get(0);
        assertEquals("Lenovo ThinkPad X1", this.invokeGetter(device, "getName"));
    }

    @Test
    void testEditWithoutSelectionShowsStatusMessage() throws Exception {
        Object controller = this.loadController();
        Label actionStatusLabel = this.getFieldValue(controller, "actionStatusLabel", Label.class);

        this.runOnFxThreadAndWait(() -> this.invokeMethod(controller, "onEditSelectedDevice"));

        assertEquals("Select a device to edit.", actionStatusLabel.getText());
    }

    private Object loadController() throws Exception {
        AtomicReference<Object> controllerReference = new AtomicReference<>();

        this.runOnFxThreadAndWait(() -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(MANAGEMENT_RESOURCE));
            try {
                Parent root = loader.load();
                controllerReference.set(loader.getController());
                if (root == null) {
                    throw new IllegalStateException("Failed to load Management.fxml.");
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });

        return controllerReference.get();
    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Object target, String fieldName, Class<T> type) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(target);
    }

    private String invokeGetter(Object target, String methodName) throws Exception {
        Method method = target.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (String) method.invoke(target);
    }

    private void invokeMethod(Object target, String methodName) {
        try {
            Method method = target.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(target);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private void runOnFxThreadAndWait(FxAction action) throws Exception {
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable exception) {
                failure.set(exception);
            } finally {
                latch.countDown();
            }
        });

        if (!latch.await(TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX action timed out.");
        }
        if (failure.get() != null) {
            throw new Exception(failure.get());
        }
    }

    @FunctionalInterface
    private interface FxAction {
        void run() throws Exception;
    }
}
