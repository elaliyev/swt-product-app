package com.espress;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import com.espress.model.Product;
import com.espress.service.FileService;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;

public class Products {

	protected Shell shell;
	private Text searchInputText;
	private Table productsTable;

	private List<Product> products;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Products window = new Products(new ArrayList<>());
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Products(List<Product> products) {
		this.products = products;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(863, 347);
		shell.setText("SWT Application");

		searchInputText = new Text(shell, SWT.BORDER);
		searchInputText.setToolTipText("search by id, name and description");
		searchInputText.setBounds(79, 13, 148, 19);

		Button searchButton = new Button(shell, SWT.NONE);
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Product> products = FileService.findProductByAllFields(searchInputText.getText());
				refreshTable(products);
			}
		});
		searchButton.setBounds(248, 10, 94, 27);
		searchButton.setText("Search");

		productsTable = new Table(shell, SWT.V_SCROLL | SWT.H_SCROLL);
		productsTable.setHeaderVisible(true);
		productsTable.setLinesVisible(true);

		productsTable.setBounds(0, 38, 862, 285);
		productsTable.setHeaderVisible(true);
		productsTable.setLinesVisible(true);

		TableColumn idColumn = new TableColumn(productsTable, SWT.CENTER);
		TableColumn productNameColumn = new TableColumn(productsTable, SWT.CENTER);
		TableColumn typeColumn = new TableColumn(productsTable, SWT.CENTER);
		TableColumn descriptionColumn = new TableColumn(productsTable, SWT.CENTER);
		idColumn.setText("Id");
		productNameColumn.setText("Product Name");
		typeColumn.setText("Type");
		descriptionColumn.setText("Description");

		idColumn.setWidth(155);
		productNameColumn.setWidth(189);
		typeColumn.setWidth(119);
		descriptionColumn.setWidth(400);
		productsTable.setHeaderVisible(true);
		
		Label lblClickToThe = new Label(shell, SWT.NONE);
		lblClickToThe.setBounds(713, 16, 140, 14);
		lblClickToThe.setText("* click to the row for edit");

		refreshTable(products);

		productsTable.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				String string = "";
				TableItem[] selection = productsTable.getSelection();
				String selectedId = selection[0].getText();
				Product product = products.stream().filter(f -> f.getId().equals(selectedId)).findAny().get();

				ProductEdit productEdit = new ProductEdit(product);
				shell.close();
				productEdit.open();

			}
		});

	}

	public void refreshTable(List<Product> products) {
		productsTable.removeAll();
		for (Product product : products) {
			TableItem item = new TableItem(productsTable, SWT.NONE);
			item.setText(new String[] { product.getId(), product.getName(), product.getType().name(),
					product.getDescription() });
		}
	}
}
