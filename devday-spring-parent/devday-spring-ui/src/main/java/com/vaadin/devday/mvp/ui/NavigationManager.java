package com.vaadin.devday.mvp.ui;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.NavigationStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import com.vaadin.devday.mvp.ui.menu.NavigationEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.UriFragmentManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;

@UIScope
@SpringComponent
public class NavigationManager {

	@Autowired
	private SpringViewProvider viewProvider;

	@Autowired
	private ApplicationEventPublisher eventPublished;

	@Autowired
	private ApplicationViewDisplay viewDisplay;

	private Navigator navigator;

	@Autowired
	private VaadinUI ui;

	@PostConstruct
	protected void initialize() {
		navigator = new Navigator(ui,  viewDisplay);
		navigator.addProvider(viewProvider);
	}

	public void navigateTo(Views view) {
		eventPublished.publishEvent(new NavigationEvent(view));
	}

	@EventListener
	protected void onNavigationEvent(NavigationEvent event) {
		Optional<Views> targetView = Arrays.stream(Views.values())
				.filter(v -> v.getId().equals(event.getTarget().getId())).findFirst();
		if (targetView.isPresent()) {
			navigator.navigateTo(event.getTarget().getId());
		} else {
			// fallback
			navigator.navigateTo(Views.CUSTOMER.getId());
		}
	}
}
