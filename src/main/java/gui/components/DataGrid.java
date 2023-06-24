package gui.components;

import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import javafx.scene.control.Skin;

public class DataGrid extends MFXPaginatedTableView {
    public DataGrid() {
        super();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DataGridSkin<>(this, rowsFlow);
    }

    // TODO: add an onRowClick event! (see AdministratorScreen.java AND jira ticket)
}
