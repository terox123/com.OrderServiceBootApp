package com.OrderServiceBootApp.com.OrderServiceBootApp;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Customer;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Order;
import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.OrderRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.ProductRepository;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.CustomerService;
import com.OrderServiceBootApp.com.OrderServiceBootApp.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тесты для OrderService.
 * Используется JUnit 5 и Mockito для имитации зависимостей.
 */
class OrderServiceTest {

    // Моки зависимостей, чтобы не подключать реальную БД
    @Mock private OrderRepository orderRepository;
    @Mock private CustomerService customerService;
    @Mock private ProductRepository productRepository;

    // Сервис, который тестируем
    @InjectMocks private OrderService orderService;

    @BeforeEach
    void setUp() {
        // Инициализирует все поля с аннотациями @Mock и @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяем, что getById возвращает заказ, если он найден.
     */
    @Test
    void testGetById_found() {
        Order order = new Order();
        order.setId(1L);

        // Настраиваем поведение мока — вернуть заказ при вызове findById
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getById(1L);

        // Проверяем, что вернулся нужный объект
        assertEquals(1L, result.getId());
        verify(orderRepository).findById(1L);
    }

    /**
     * Проверяем, что getById выбрасывает исключение, если заказ не найден.
     */
    @Test
    void testGetById_notFound() {
        // Возвращаем пустой Optional — заказ не найден
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Ожидаем EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> orderService.getById(1L));
    }

    /**
     * Проверяем успешное создание заказа:
     * - клиент найден
     * - продукты найдены
     * - количество уменьшается
     * - заказ сохраняется
     */
    @Test
    void testCreate_success() {
        // Создаём клиента
        Customer customer = new Customer();
        customer.setId(1L);

        // Создаём товар с количеством > 0
        Product product1 = new Product();
        product1.setId(2L);
        product1.setQuantity(3);
        product1.setName("TestProduct");

        // Мокаем вызовы зависимостей
        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(productRepository.findAllById(List.of(2L))).thenReturn(List.of(product1));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        // Действие
        Order result = orderService.create(1L, List.of(2L));

        // Проверки
        assertNotNull(result);
        assertEquals(customer, result.getCustomer());
        assertEquals(1, result.getProducts().size());
        assertEquals(2, product1.getQuantity()); // количество товара уменьшилось на 1
        verify(orderRepository).save(any(Order.class));
    }

    /**
     * Проверяем, что при попытке создать заказ с товаром, у которого количество 0,
     * выбрасывается исключение "Product out of stock".
     */
    @Test
    void testCreate_productOutOfStock() {
        Customer customer = new Customer();
        Product product = new Product();
        product.setQuantity(0);
        product.setName("Empty");

        when(customerService.findCustomerById(1L)).thenReturn(customer);
        when(productRepository.findAllById(List.of(2L))).thenReturn(List.of(product));

        assertThrows(RuntimeException.class, () -> orderService.create(1L, List.of(2L)));
    }

    /**
     * Проверяем, что если некоторые товары не найдены по ID — выбрасывается EntityNotFoundException.
     */
    @Test
    void testCreate_missingProduct() {
        Customer customer = new Customer();

        when(customerService.findCustomerById(1L)).thenReturn(customer);
        // Репозиторий вернёт только один товар, хотя мы просим два
        when(productRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(new Product()));

        assertThrows(EntityNotFoundException.class, () -> orderService.create(1L, List.of(1L, 2L)));
    }

    /**
     * Проверяем успешное удаление заказа.
     */
    @Test
    void testDelete_success() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        orderService.delete(1L);

        // Проверяем, что deleteById был вызван
        verify(orderRepository).deleteById(1L);
    }
}
