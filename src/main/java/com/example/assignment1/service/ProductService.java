package com.example.assignment1.service;

import java.util.Map;
import java.util.Optional;
import com.example.assignment1.Exception.DataNotFoundException;
import com.example.assignment1.Exception.InvalidUserInputException;
import com.example.assignment1.Exception.UserAuthrizationException;
import com.example.assignment1.entity.Product;
import com.example.assignment1.entity.UserInfo;
import com.example.assignment1.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    UserService userService;

    public Product createProduct(Product product, String userName)
            throws UserAuthrizationException, InvalidUserInputException {
        // TODO Auto-generated method stub
        UserInfo userobj = userService.loadUserByUsername(userName);
        if (userobj != null) {
            checkSku(1L, userobj.getId(), product.getSku(), "PostCheck");
            product.setOwnerUserId(userobj.getId());
            productRepo.saveAndFlush(product);
            return product;
        }
        // throw auth error
        throw new UserAuthrizationException("UnAuthrized username dose not exists");
    }

    public Product checkSku(Long id, Long ownerId, String sku, String check) throws InvalidUserInputException {
        Product p;
        if (check.equals("PostCheck"))
            p = productRepo.findProductByownerUserIdAndSku(ownerId, sku);
        else
            p = productRepo.checkProductSku(id, ownerId, sku);
        System.out.println(p);
        if (p == null)
            return p;
        throw new InvalidUserInputException("SKU Value Exists already");
    }

    public Product getProduct(Long productId) throws DataNotFoundException {
        Optional<Product> product = productRepo.findById(productId);
        if (product.isPresent())
            return product.get();
        throw new DataNotFoundException("No product associated with given Id: " + productId);
    }

    public String updateProductDetails(Long productId, Product product)
            throws DataNotFoundException, InvalidUserInputException {
        Product p = getProduct(productId);
        checkSku(p.getId(), p.getOwnerUserId(), product.getSku(), "PutCheck");
        p.setId(productId);
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setSku(product.getSku());
        p.setManufacturer(product.getManufacturer());
        p.setQuantity(product.getQuantity());
        productRepo.saveAndFlush(p);
        return "Updated Product Details";
    }

    public String deleteProductDetails(Long productId) throws DataNotFoundException {
        Product p = getProduct(productId);
        productRepo.deleteById(p.getId());
        return "Deleted Product";
    }

    public String patchProductDetails(Long productId, Map<String, Object> updates)
            throws DataNotFoundException, InvalidUserInputException {
        Product p = getProduct(productId);
        if(updates.size()==0)
            throw new InvalidUserInputException("Request can't be empty");
        for (Map.Entry<String, Object> map : updates.entrySet()) {
            switch (map.getKey()) {
                case "name":
                    String name = (String) map.getValue();
                    if (name.isBlank() || name.isEmpty()||name==null)
                        throw new InvalidUserInputException("Product Name can't be null/empty");
                    else
                        p.setName(name);
                    break;
                case "description":
                    String description = (String) map.getValue();
                    if (description.isBlank() || description.isEmpty()||description==null)
                        throw new InvalidUserInputException("Product description can't be null/empty");
                    p.setDescription(description);
                    break;
                case "sku":
                    String sku = (String) map.getValue();
                    if (sku.isBlank() || sku.isEmpty()||sku==null)
                        throw new InvalidUserInputException("Product SKU can't be null/empty");
                    checkSku(p.getId(), p.getOwnerUserId(), sku, "PostCheck");
                    p.setSku(sku);
                    break;
                case "manufacture":
                    String manufacture = (String) map.getValue();
                    if (manufacture.isBlank() || manufacture.isEmpty()||manufacture==null)
                        throw new InvalidUserInputException("Product manufacture can't be null/empty");
                    p.setManufacturer(manufacture);
                    break;
                case "quantity":
                    Integer quantity = (Integer) map.getValue();
                    if (quantity < 1 || quantity > 100)
                        throw new InvalidUserInputException("Product quantity should be btw 1 and 100");
                    p.setQuantity(quantity);
                    break;
            }
        }
        productRepo.saveAndFlush(p);
        return "Updated Product Details";
    }

}