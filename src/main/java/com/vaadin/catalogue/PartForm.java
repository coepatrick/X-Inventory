package com.vaadin.catalogue;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.catalogue.backend.Part;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class PartForm extends FormLayout {

    Button save = new Button("Save", this::save);
    Button cancel = new Button("Cancel", this::cancel);
    TextField partName = new TextField("Part Name");
    TextField partNumber = new TextField("Part Number");
    TextField quantity = new TextField("Quantity");
    Button addQuantity = new Button("+", this::addQuantity);
    Button removeQuantity = new Button("-", this::removeQuantity);
    Button deletePart = new Button("Delete", this::deletePart);

    Part part;

    // Easily bind forms to beans and manage validation and buffering
    BeanFieldGroup<Part> formFieldBindings;

    public PartForm() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        actions.setSpacing(true);
        
        HorizontalLayout addRemove = new HorizontalLayout(addQuantity, removeQuantity);
        actions.setSpacing(true);

		addComponents(actions, partName, partNumber, quantity, deletePart);
		
		
    }

    public void save(Button.ClickEvent event) {
        try {
            // Commit the fields from UI
            formFieldBindings.commit();

            // Save to backend
            getUI().service.save(part);

            String msg = String.format("Saved '%s %s'.",
                    part.getPartName(),
                    part.getPartNumber());
            Notification.show(msg,Type.TRAY_NOTIFICATION);
            getUI().refreshParts();
        } catch (FieldGroup.CommitException e) {
            // Validation exceptions could be shown here
        }
    }

    public void cancel(Button.ClickEvent event) {
        Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
        getUI().partList.select(null);
    }
    
    private int temp = 0;
    
    public void addQuantity(Button.ClickEvent event) {
    	temp = part.getQuantity();
    	temp++;
    	part.setQuantity(temp);
    }
    
    public void removeQuantity(Button.ClickEvent event) {
    	temp = part.getQuantity();
    	temp++;
    	part.setQuantity(temp);
    	//part.setQuantity((part.getQuantity()));
    }
    
    public void deletePart(Button.ClickEvent event) {
    	getUI().service.delete(part);
    	 String msg = String.format("Deleted '%s %s'.",
                 part.getPartName(),
                 part.getPartNumber());
         Notification.show(msg,Type.TRAY_NOTIFICATION);
         getUI().refreshParts();
    	
    	
    }

    void edit(Part part) {
        this.part = part;
        if(part != null) {
            // Bind the properties of the part to fields in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(part, this);
            partName.focus();
        }
        setVisible(part != null);
    }

    @Override
    public CatalogueUI getUI() {
        return (CatalogueUI) super.getUI();
    }

}
