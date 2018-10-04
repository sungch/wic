package bettercare.wic.dal.repository;

import bettercare.wic.dal.model.*;
import org.springframework.stereotype.Repository;


@Repository
public class RespositoryService extends WicService {

  // Category

  public Category findCategory(Long id) {
    return super.find(Category.class, id);
  }

  public Category createCategory(Category category) {
    return super.create(category);
  }

  public Category mergeCategory(Category category) {
    return super.merge(category);
  }

  public void removeCategory(Category category) {
    super.remove(category);
  }

  // Customer

  public Customer findCustomer(Long id) {
    return super.find(Customer.class, id);
  }

  public Customer createCustomer(Customer obj) {
    return super.create(obj);
  }

  public Customer mergeCustomer(Customer obj) {
    return super.merge(obj);
  }

  public void removeCustomer(Customer obj) {
    super.remove(obj);
  }

  // Delivery

  public Delivery findDelivery(Long id) {
    return super.find(Delivery.class, id);
  }

  public Delivery createCustomer(Delivery obj) {
    return super.create(obj);
  }

  public Delivery mergeCustomer(Delivery obj) {
    return super.merge(obj);
  }

  public void removeCustomer(Delivery obj) {
    super.remove(obj);
  }

  // MissingProduct

  public MissingProduct findMissingProduct(Long id) {
    return super.find(MissingProduct.class, id);
  }

  public MissingProduct createMissingProduct(MissingProduct obj) {
    return super.create(obj);
  }

  public MissingProduct mergeMissingProduct(MissingProduct obj) {
    return super.merge(obj);
  }

  public void removeMissingProduct(MissingProduct obj) {
    super.remove(obj);
  }

  // Order

  public Order findOrder(Long id) {
    return super.find(Order.class, id);
  }

  public Order createOrder(Order obj) {
    return super.create(obj);
  }

  public Order mergeOrder(Order obj) {
    return super.merge(obj);
  }

  public void removeOrder(Order obj) {
    super.remove(obj);
  }

  // Product

  public Product findProduct(Long id) {
    return super.find(Product.class, id);
  }

  public Product createProduct(Product obj) {
    return super.create(obj);
  }

  public Product mergeProduct(Product obj) {
    return super.merge(obj);
  }

  public void removeProduct(Product obj) {
    super.remove(obj);
  }

  // Voucher

  public Voucher findVoucher(Long id) {
    return super.find(Voucher.class, id);
  }

  public Voucher createVoucher(Voucher obj) {
    return super.create(obj);
  }

  public Voucher mergeVoucher(Voucher obj) {
    return super.merge(obj);
  }

  public void removeVoucher(Voucher obj) {
    super.remove(obj);
  }

}
