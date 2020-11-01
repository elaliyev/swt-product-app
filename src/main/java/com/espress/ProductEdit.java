package com.espress;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.espress.model.Product;
import com.espress.model.Type;
import com.espress.service.FileService;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ProductEdit {

	protected Shell shell;

	private Product product;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ProductEdit window = new ProductEdit(null);
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProductEdit(Product product) {
		this.product = product;
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
		shell.setSize(653, 405);
		shell.setText("SWT Application");

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setFont(SWTResourceManager.getFont(".AppleSystemUIFont", 12, SWT.NORMAL));
		lblName.setBounds(25, 24, 59, 27);
		lblName.setText("Name");

		Text nameInputText = new Text(shell, SWT.BORDER);
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

		nameInputText.setText(product.getName());
		descriptionText.setText(product.getDescription());
		typeComboBox.select(product.getType().ordinal());

		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.setBounds(25, 216, 94, 33);
		btnSave.setText("Save");

		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				product.setName(nameInputText.getText());
				product.setDescription(descriptionText.getText());
				product.setType(Type.fromId(typeComboBox.getSelectionIndex()));

				try {
					FileService.editProduct(product);
					MessageBox dialog = new MessageBox(shell, SWT.OK);
					dialog.setText("Info");
					dialog.setMessage("Product Updated. ");
					dialog.open();
					List<Product> list = FileService.readAllProducts();
					Products productForm = new Products(list);
					shell.close();
					productForm.open();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.out.println(product.toString());
			}
		});
		btnSave.setBounds(319, 97, 94, 27);
		btnSave.setText("Save");

	}
}
