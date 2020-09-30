package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    //-------------------Retrieve All Customers--------------------------------------------------------
    @RequestMapping(value = "/product/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProduct() {
        List<Product> products = productService.findAllProduct();
        if (products.isEmpty()) {
            return new ResponseEntity<List<Product>>( HttpStatus.NO_CONTENT );//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>( products, HttpStatus.OK );
    }
    //-------------------Retrieve Single Customer--------------------------------------------------------
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        System.out.println( "Fetching Product with id " + id );
        Product product = productService.findById( id );
        if (product == null) {
            System.out.println( "Product with id " + id + " not found" );
            return new ResponseEntity<Product>( HttpStatus.NOT_FOUND );
        }
        return new ResponseEntity<Product>( product, HttpStatus.OK );
    }
    //-------------------Create a Customer--------------------------------------------------------
    @RequestMapping(value = "/product/", method = RequestMethod.POST)
    public ResponseEntity<Void> createCustomer(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
        System.out.println( "Creating product " + product.getProductName() );
        productService.save( product );
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation( ucBuilder.path( "/customers/{id}" ).buildAndExpand( product.getProductId() ).toUri() );
        return new ResponseEntity<Void>( headers, HttpStatus.CREATED );
    }

    //------------------- Update a Customer --------------------------------------------------------
    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        System.out.println( "Updating Product " + id );

        Product currentProduct = productService.findById( id );

        if (currentProduct == null) {
            System.out.println( "Product with id " + id + " not found" );
            return new ResponseEntity<Product>( HttpStatus.NOT_FOUND );
        }

        currentProduct.setProductName( product.getProductName() );
        currentProduct.setDescription( product.getDescription() );
        currentProduct.setProductId( product.getProductId() );

        productService.save( currentProduct );
        return new ResponseEntity<Product>( currentProduct, HttpStatus.OK );
    }

    //------------------- Delete a Customer --------------------------------------------------------

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteCustomer(@PathVariable("id") long id) {
        System.out.println( "Fetching & Deleting Product with id " + id );

        Product product = productService.findById( id );
        if (product == null) {
            System.out.println( "Unable to delete. Product with id " + id + " not found" );
            return new ResponseEntity<Product>( HttpStatus.NOT_FOUND );
        }

        productService.remove( id );
        return new ResponseEntity<Product>( HttpStatus.NO_CONTENT );
    }
}
