package com.vaadin.catalogue;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.catalogue.backend.Part;
import com.vaadin.catalogue.backend.PartService;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Title("X-Inventory spare parts catalogue") //web page title
@Theme("reindeer") //Built in Vaadin Theme "reindeer"
public class CatalogueUI extends UI { //As I understand, our CatalogueUI class extends on the existing UI class provided by vaadin

    TextField filter = new TextField();  //create object to be placed later for fultering catalogue 
    Grid partList = new Grid(); //create grid for part list
    Button newPart = new Button("Add part"); //create button

    PartForm partForm = new PartForm(); //custom component class (not exactly sure what this means, but used to be able to open form later)

      PartService service = PartService.createService(); //constructs partservices instance

    @Override //used to "initialize and configure the visible user interface.
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {
        newPart.addClickListener(e -> partForm.edit(new Part())); //'listens' for click, then opens part form

        filter.setInputPrompt("Find part..."); //preexisting text in filter text box
        filter.addTextChangeListener(e -> refreshParts(e.getText())); //refresh parts list as text is entered

        partList.setContainerDataSource(new BeanItemContainer<>(Part.class));
        partList.setColumnOrder("partName", "partNumber", "quantity"); // set up columns for partlist grid
        partList.removeColumn("id"); //hide id column (not interesting to user)
        partList.setSelectionMode(Grid.SelectionMode.SINGLE); //select single item at a time
        partList.addSelectionListener(e -> partForm.edit((Part) partList.getSelectedRow())); //listen to selection, open edit with relevant data already in inputs
        refreshParts(); //refresh part list
    }

    private void buildLayout() { //used to build layout
        HorizontalLayout actions = new HorizontalLayout(filter, newPart);
        actions.setWidth("100%"); //what portion of screen to take
        filter.setWidth("100%"); //what portion filter textbox takes
        actions.setExpandRatio(filter, 1); //expand (leaves no gaps)

        VerticalLayout left = new VerticalLayout(actions, partList);
        left.setSizeFull();
        partList.setSizeFull();
        left.setExpandRatio(partList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, partForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        // Split and allow resizing
        setContent(mainLayout);
    }

    void refreshParts() { //refresh part list based on value in filter textbox
        refreshParts(filter.getValue());
    }

    private void refreshParts(String stringFilter) {  //refreshparts
        partList.setContainerDataSource(new BeanItemContainer<>(
                Part.class, service.findAll(stringFilter)));
        partForm.setVisible(false);
    }

    @WebServlet(urlPatterns = "/*")  // sets up servlet, default local address
    @VaadinServletConfiguration(ui = CatalogueUI.class, productionMode = false) //default settings... production mode off.. apparently you can reconfigure code live?
    public static class MyUIServlet extends VaadinServlet { //used to initiate servlet. I assume.
    }


}
