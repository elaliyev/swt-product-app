package com.espress;

import java.io.IOException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import com.espress.model.Product;
import com.espress.model.Type;
import com.espress.service.FileService;

import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class Home {
	private static Text nameInputText;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(683, 392);
		shell.setText("SWT Application");

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 12, SWT.NORMAL));
		lblName.setBounds(25, 24, 59, 27);
		lblName.setText("Name");

		nameInputText = new Text(shell, SWT.BORDER);
		nameInputText.setBounds(121, 21, 148, 30);
		nameInputText.setTextLimit(100);

		Label lblType = new Label(shell, SWT.NONE);
		lblType.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 12, SWT.NORMAL));
		lblType.setBounds(305, 24, 59, 27);
		lblType.setText("Type");

		final CCombo typeComboBox = new CCombo(shell, SWT.BORDER);
		typeComboBox.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 12, SWT.NORMAL));
		typeComboBox.setEditable(false);
		typeComboBox.setItems(new String[] { "DevKit", "Soc's", "Module" });
		typeComboBox.setBounds(370, 23, 94, 28);
		typeComboBox.select(0);

		Label lblDescription = new Label(shell, SWT.NONE);
		lblDescription.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 12, SWT.NORMAL));
		lblDescription.setBounds(25, 75, 90, 27);
		lblDescription.setText("Description");

		final StyledText descriptionText = new StyledText(shell, SWT.BORDER);
		descriptionText.setBounds(25, 118, 246, 69);
		descriptionText.setTextLimit(300);

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.setBounds(25, 216, 94, 33);
		btnSave.setText("Save");
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Type type = Type.fromId(typeComboBox.getSelectionIndex());
				String name = nameInputText.getText();
				String description = descriptionText.getText();
				Product product = new Product(name, type, description);
				try {
					FileService.addProduct(product);
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Info");
					dialog.setMessage("Product Saved. ");
					dialog.open();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

			}
		});

		Button btnAllProducts = new Button(shell, SWT.NONE);
		btnAllProducts.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				List<Product> list = FileService.readAllProducts();
				System.out.println("before size=" + list.size());

				Products productForm = new Products(list);
				productForm.open();

			}
		});
		btnAllProducts.setBounds(140, 216, 94, 33);
		btnAllProducts.setText("all products");

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
