package com.espress.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.espress.model.Data;
import com.espress.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileService {

	private static final String fileName = "product.json";
	private static File file = new File(fileName);

	public static void addProduct(Product product) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		try {
			if (!file.exists() || file.length() == 0) {
				Data data = new Data();
				data.setProducts(Arrays.asList(product));
				mapper.writeValue(Paths.get(fileName).toFile(), data);
			} else {
				Data data = mapper.readValue(Paths.get(fileName).toFile(), Data.class);
				List<Product> products = data.getProducts();
				products.add(product);
				mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(fileName).toFile(), data);
			}
		} catch (Exception ex) {
			throw new Exception("Error occured when add product");

		}
	}

	public static void editProduct(Product product) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		File file = new File("product.json");
		try {
			if (file.exists() && file.length() > 0) {
				Data data = mapper.readValue(Paths.get(fileName).toFile(), Data.class);

				List<Product> products = data.getProducts().stream().filter(p -> !p.getId().equals(product.getId()))
						.collect(Collectors.toList());
				products.add(product);
				data.setProducts(products);
				mapper.writerWithDefaultPrettyPrinter().writeValue(Paths.get(fileName).toFile(), data);
			}

		} catch (Exception ex) {
			throw new Exception("Error occured when add product");

		}
	}

	public static List<Product> findProductByAllFields(String field) {

		List<Product> products = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			if (file.exists() && file.length() > 0) {
				Data data = mapper.readValue(Paths.get(fileName).toFile(), Data.class);
				products.addAll(data
						.getProducts().stream().filter(f -> f.getId().contains(field)
								|| f.getDescription().contains(field) || f.getName().contains(field))
						.collect(Collectors.toList()));

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return products;
	}

	public static List<Product> readAllProducts() {

		List<Product> products = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			if (file.exists() && file.length() > 0) {
				Data data = mapper.readValue(Paths.get(fileName).toFile(), Data.class);
				products.addAll(data.getProducts());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return products;
	}

}
