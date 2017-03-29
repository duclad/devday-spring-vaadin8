package com.vaadin.devday.mvp.ui.customer;

import com.vaadin.contextmenu.GridContextMenu;
import com.vaadin.devday.mvp.ui.AbstractView;
import com.vaadin.devday.mvp.ui.customer.editor.CustomerEditorBean;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@SpringView(name = "")
public class CustomerViewBean extends AbstractView<CustomerViewPresenter> implements CustomerView, View {

    private Grid<Customer> customerGrid;

    @Autowired
    private ApplicationEventPublisher customerEventSource;

    @Autowired
    @Lazy
    private CustomerEditorBean customerEditor;


    public CustomerViewBean() {
        setSizeFull();

        customerGrid = buildCustomerTable();
        customerGrid.setSizeFull();

        VerticalLayout layout = new VerticalLayout(customerGrid);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();

        setCompositionRoot(layout);
    }

    private Grid<Customer> buildCustomerTable() {
        Grid<Customer> grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(Customer::getFirstName).setCaption("First Name");
        grid.addColumn(Customer::getLastName).setCaption("Last Name");
        GridContextMenu<Customer> gridContextMenu = new GridContextMenu<>(grid);
        gridContextMenu.addGridBodyContextMenuListener(gridContextMenuOpenEvent -> {
            gridContextMenu.addItem("Add",null);
            gridContextMenu.addItem("Edit", null);
            gridContextMenu.addItem("Remove", null);
        });
        return grid;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        getPresenter().onViewEnter();
    }

    @Override
    public void populateCustomers(List<Customer> customers) {
        customerGrid.setItems(customers);
    }

    @Override
    public void openCustomerEditor(Customer customer) {
        customerEditor.setCustomer(customer);
    }

    @Override
    @Autowired
    protected void injectPresenter(CustomerViewPresenter presenter) {
        setPresenter(presenter);
    }
}
