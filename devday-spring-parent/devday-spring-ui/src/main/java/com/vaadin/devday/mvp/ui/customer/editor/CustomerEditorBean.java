package com.vaadin.devday.mvp.ui.customer.editor;

import com.vaadin.data.Binder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.devday.mvp.ui.AbstractPopupView;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@Scope("prototype")
public class CustomerEditorBean extends AbstractPopupView<CustomerEditorPresenter> implements CustomerEditor {
    private FormLayout form;
    final Binder<Customer> binder = new Binder<>();

    public CustomerEditorBean() {
        form = new FormLayout();
        form.setMargin(true);
        form.setSpacing(true);


        TextField firstNameField = new TextField("First name");
        TextField lastNameField = new TextField("Last name");

        form.addComponents(firstNameField, lastNameField);

        binder.forField(firstNameField).bind("firstName");
        binder.forField(lastNameField).bind("lastName");

        Button save = new Button("Save", e -> getPresenter().onFormSaveClicked());
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        Button cancel = new Button("Cancel", e -> close());

        HorizontalLayout buttonLayout = new HorizontalLayout(cancel, save);
        buttonLayout.setSpacing(true);
        buttonLayout.setWidth(100, Unit.PERCENTAGE);
        buttonLayout.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        buttonLayout.setExpandRatio(cancel, 1);
        buttonLayout.setComponentAlignment(cancel, Alignment.TOP_RIGHT);

        VerticalLayout layout = new VerticalLayout(form, buttonLayout);
        layout.setSpacing(true);

        setHeightUndefined();

        setCompositionRoot(layout);
    }

    public void setCustomer(Customer customer) {
        binder.setBean(customer);
    }

    @Override
    protected int getWindowPixelWidth() {
        return 350;
    }

    @Override
    protected boolean isModal() {
        return false;
    }

    @Override
    @Autowired
    protected void injectPresenter(CustomerEditorPresenter presenter) {
        setPresenter(presenter);
    }

    @Override
    public boolean isFormContentValid() {
        return binder.isValid();
    }

    @Override
    public Customer fetchFormContent() {
        return binder.getBean();
    }

    @Override
    public void showSaveFailed() {
        Notification.show("Saving failed", Type.ERROR_MESSAGE);
    }

    @Override
    public void showSaveSucceeded() {
        Notification.show("Data saved!", Type.TRAY_NOTIFICATION);
    }
}
