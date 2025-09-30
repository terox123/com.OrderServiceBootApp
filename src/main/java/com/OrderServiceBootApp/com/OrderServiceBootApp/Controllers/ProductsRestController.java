package com.OrderServiceBootApp.com.OrderServiceBootApp.Controllers;


import com.OrderServiceBootApp.com.OrderServiceBootApp.DTO.ProductDTO;
import com.OrderServiceBootApp.com.OrderServiceBootApp.kafka.KafkaProducerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsRestController {

private final ProductService productService;
private final KafkaProducerService kafkaProducerService;
private final ModelMapper modelMapper;

    @Autowired
    public ProductsRestController(ProductService productService, KafkaProducerService kafkaProducerService, ModelMapper modelMapper) {
        this.productService = productService;
        this.kafkaProducerService = kafkaProducerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(){
        List<ProductDTO> orderDTOList = productService.getAll()
                .stream()
                .map(this::convertToProductDTO)
                .toList();
        kafkaProducerService.sendMessage("my-topic", "key1", "All products was received");
        return ResponseEntity.ok(orderDTOList);

    }

    @GetMapping("/{id}")
public ResponseEntity<ProductDTO> getById(@PathVariable("id")long id){
ProductDTO productDTO = convertToProductDTO(productService.getById(id));
        kafkaProducerService.sendMessage("my-topic", "key1", "Product with id " + id + " was received");
    return ResponseEntity.ok(productDTO);
    }

@PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product, BindingResult bindingResult){
if(bindingResult.hasErrors()){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(product);
}

productService.create(product);
    kafkaProducerService.sendMessage("my-topic", "key1", "Product " + product.toString() + "was saved");

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
}


@PostMapping("/{id}/delete")
public ResponseEntity<Product> delete(@PathVariable("id")long id){

        productService.delete(id);
kafkaProducerService.sendMessage("my-topic", "key1", "product with id " + id + " was deleted");
        return ResponseEntity.status(HttpStatus.OK).body(productService.getById(id));
}

private ProductDTO convertToProductDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
}


}
