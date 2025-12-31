package com.backend.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}




        @Bean
        public CommandLineRunner seedData() {
            return args -> {

                Faker faker = new Faker(new Random(42));

                // If products and users already exist, skip full seeding but still attempt to add ratings/reviews/wishlist/orders only if missing
                boolean needFullSeed = (productRepository.count() == 0) || (userRepository.count() == 0);

                List<Product> products = productRepository.findAll();
                List<User> users = userRepository.findAll();

                if (needFullSeed) {
                    // create categories
                    List<Category> categories = new ArrayList<>();
                    String[] tags = {"electronics", "fashion", "books", "sports", "home", "beauty"};

                    for (int i = 0; i < 6; i++) {
                        Category c = new Category();
                        c.setName(faker.commerce().department());
                        c.setLevel(faker.number().numberBetween(0, 3));

                        c.setCategory_img("https://picsum.photos/seed/" + i + "/200/200");

                        categories.add(c);
                    }

                    categoryRepository.saveAll(categories);

                    // create sellers and stores
                    List<Seller> sellers = new ArrayList<>();
                    List<Store> stores = new ArrayList<>();
                    for (int i = 0; i < 8; i++) {
                        Seller s = new Seller();
                        s.setCompanyName(faker.company().name());
                        s.setShortName(s.getCompanyName().substring(0, Math.min(10, s.getCompanyName().length())));
                        s.setEmail(faker.internet().emailAddress());
                        s.setCity(faker.address().city());
                        s.setPinCode(faker.address().zipCode());
                        s.setGstNumber("GST" + faker.number().digits(10));

                        sellers.add(s);

                        Store st = new Store();
                        st.setBrand(s.getCompanyName());
                        st.setDescription(faker.lorem().sentence());
                        st.setCreateAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0,40)));
                        st.setUpdateAt(LocalDateTime.now());
                        stores.add(st);
                    }
                    sellerRepository.saveAll(sellers);
                    storeRepository.saveAll(stores);

                    // create products
                    products = new ArrayList<>();
                    for (int i = 0; i < 40; i++) {
                        Product p = new Product();
                        p.setTitle(faker.commerce().productName());
                        p.setDescription(faker.lorem().paragraph());
                        int price = faker.number().numberBetween(200, 20000);
                        p.setPrice(price);
                        p.setDiscountedPrice((int)(price * (0.6 + faker.random().nextDouble() * 0.3)));
                        p.setQuantity(faker.number().numberBetween(0,200));
                        p.setDoublePercent(faker.number().numberBetween(0,100));
                        p.setBrand(faker.company().name());
                        p.setColor(faker.color().name());
                        p.setSeller(sellers.get(faker.number().numberBetween(0, sellers.size())));
                        p.setImageUrl("https://picsum.photos/seed/" + i + "/400/400");
                        p.setCategory(categories.get(faker.number().numberBetween(0, categories.size())));
                        p.setCreated_At(LocalDateTime.now().minusDays(faker.number().numberBetween(0,90)));
                        p.setUpdatedAt(LocalDateTime.now());
                        products.add(p);
                    }
                    productRepository.saveAll(products);

                    // create users
                    users = new ArrayList<>();
                    User admin = new User();
                    admin.setFirstName("Admin");
                    admin.setLastName("User");
                    admin.setEmail("admin@local.test");
                    admin.setPassword("{noop}admin");
                    admin.setRoles(UserRole.ROLE_ADMIN);
                    admin.setMobile(faker.phoneNumber().cellPhone());
                    admin.setCreateAt(LocalDateTime.now());
                    users.add(admin);

                    for (int i = 0; i < 12; i++) {
                        User u = new User();
                        u.setFirstName(faker.name().firstName());
                        u.setLastName(faker.name().lastName());
                        u.setEmail("user" + i + "@local.test");
                        u.setPassword("{noop}password");
                        u.setRoles(UserRole.ROLE_USER);
                        u.setMobile(faker.phoneNumber().cellPhone());
                        u.setCreateAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0,30)));
                        users.add(u);
                    }
                    userRepository.saveAll(users);

                    // addresses
                    List<Address> addresses = new ArrayList<>();
                    for (User u : users) {
                        Address a = new Address();
                        a.setFirstName(u.getFirstName());
                        a.setLastName(u.getLastName());
                        a.setCity(faker.address().city());
                        a.setState(faker.address().state());
                        a.setStreetAddress(faker.address().streetAddress());
                        a.setZipcode(faker.address().zipCode());
                        a.setMobile(u.getMobile());
                        a.setUser(u);
                        addresses.add(a);
                    }
                    addressRespoitory.saveAll(addresses);

                    // carts
                    List<Cart> carts = new ArrayList<>();
                    for (User u : users) {
                        Cart c = new Cart();
                        c.setUser(u);
                        c.setTotalItems(0);
                        c.setTotalPrice(0);
                        carts.add(c);
                    }
                    cartRepository.saveAll(carts);
                }

                // refresh lists
                products = productRepository.findAll();
                users = userRepository.findAll();

                // seed orders, ratings/reviews and wishlist if not present
                if (orderRepository.count() == 0) seedOrders(faker, users, products);
                if (ratingRepository.count() == 0 || reviewRepository.count() == 0) seedRatingsAndReviews(faker, users, products);
                if (wishlistRepository.count() == 0) seedWishlist(faker, users, products);

                System.out.println("✅ ALL Faker data inserted/checked successfully");
            };
        }

        // ================== ORDERS + ITEMS ==================
        private void seedOrders(Faker faker, List<User> users, List<Product> products) {

            if (products.isEmpty() || users.isEmpty()) return;

            List<Order> ordersToSave = new ArrayList<>();
            List<OrderItem> itemsToSave = new ArrayList<>();

            for (User user : users) {
                if (faker.bool().bool()) continue; // some users have orders

                Order order = new Order();
                order.setOrderId(UUID.randomUUID().toString());
                order.setUser(user);
                order.setOrderDate(LocalDateTime.now().minusDays(faker.number().numberBetween(0, 10)));
                order.setCreatedAt(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.PLACED);

                Address address = new Address();
                address.setFirstName(user.getFirstName());
                address.setLastName(user.getLastName());
                address.setCity(faker.address().city());
                address.setState(faker.address().state());
                address.setStreetAddress(faker.address().streetAddress());
                address.setZipcode(faker.address().zipCode());
                address.setMobile(user.getMobile());
                order.setShippingAddress(address);

                PaymentDetails payment = new PaymentDetails();
                payment.setPaymentMethod("ONLINE");
                payment.setPaymentStatus("SUCCESS");
                order.setPaymentDetails(payment);

                int totalPrice = 0;
                int totalItems = 0;

                int itemCount = faker.number().numberBetween(1,4);
                List<OrderItem> items = new ArrayList<>();
                for (int i = 0; i < itemCount; i++) {
                    Product product = products.get(faker.number().numberBetween(0, products.size()));

                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setProduct(product);
                    item.setQuantity(1 + faker.number().numberBetween(0,2));
                    item.setPrice(product.getPrice());
                    item.setDiscountedPrice(product.getDiscountedPrice());
                    item.setUserId(user.getId());
                    item.setDeliveryDate(LocalDateTime.now().plusDays(faker.number().numberBetween(1,10)));

                    totalPrice += product.getDiscountedPrice();
                    totalItems += item.getQuantity();

                    items.add(item);
                    itemsToSave.add(item);
                }

                order.setOrderItems(items);
                order.setTotalItems(totalItems);
                order.setTotalPrice(totalPrice);
                order.setTotalDiscountedPrice(totalPrice);

                ordersToSave.add(order);
            }

            orderRepository.saveAll(ordersToSave);
            orderItemRepository.saveAll(itemsToSave);
        }

        // ================== RATINGS + REVIEWS ==================
        private void seedRatingsAndReviews(Faker faker, List<User> users, List<Product> products) {
            if (products.isEmpty() || users.isEmpty()) return;

            List<Rating> ratings = new ArrayList<>();
            List<Review> reviews = new ArrayList<>();

            for (Product product : products) {
                int ratingCount = faker.number().numberBetween(1, 5);
                double totalRating = 0;

                for (int i = 0; i < ratingCount; i++) {
                    User user = users.get(faker.number().numberBetween(0, users.size()));

                    Rating rating = new Rating();
                    rating.setProduct(product);
                    rating.setUser(user);
                    rating.setRating(faker.number().randomDouble(1, 1, 5));
                    rating.setCreateAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0,20)));
                    ratings.add(rating);

                    Review review = new Review();
                    review.setProduct(product);
                    review.setUser(user);
                    review.setCreateAt(LocalDateTime.now().minusDays(faker.number().numberBetween(0,20)));
                    reviews.add(review);

                    totalRating += rating.getRating();
                }

                product.setNumRatings((int) ratingCount);
            }

            ratingRepository.saveAll(ratings);
            reviewRepository.saveAll(reviews);
            productRepository.saveAll(products);
        }

        // ================== WISHLIST ==================
        private void seedWishlist(Faker faker, List<User> users, List<Product> products) {
            if (users.isEmpty() || products.isEmpty()) return;

            List<WislistItems> items = new ArrayList<>();
            for (User user : users) {
                Set<Product> favs = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    Product product = products.get(faker.number().numberBetween(0, products.size()));
                    if (favs.contains(product)) continue;
                    WislistItems w = WislistItems.builder().user(user).product(product).build();
                    items.add(w);
                    favs.add(product);
                }
            }
            wishlistRepository.saveAll(items);
        }

}
