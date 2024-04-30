package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.*;
import tn.esprit.coco.serviceImp.MarketPlaceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MarketPlaceService implements MarketPlaceImpl {

    @Autowired
    private CategoryProductRepository categoryProductRepository;
    @Autowired
    private SubCategoryProductRepository subCategoryProductRepository;
    @Autowired
    private PictureProductRepository pictureProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    /*@Autowired
    private PaymentProductRepository paymentProductRepository;*/
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FavoriteProductRepository favoriteProductRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CategoryProduct createCategoryProduct(CategoryProduct categoryProduct) {
        return categoryProductRepository.save(categoryProduct);
    }

    @Override
    public List<CategoryProduct> getAllCategoryProducts() {
        return categoryProductRepository.findAll();
    }

    @Override
    public CategoryProduct getCategoryProductById(Long idCategory) {   //Seulement by id
        return categoryProductRepository.findById(idCategory).orElse(null);
    }



    @Override
    /*public CategoryProduct updateCategoryProduct(Long idCategory, CategoryProduct categoryProduct) {
        CategoryProduct existingCategoryProduct = getCategoryProductById(idCategory);
        existingCategoryProduct.setCategoryName(categoryProduct.getCategoryName());
        existingCategoryProduct.setSubCategorys(categoryProduct.getSubCategorys());
        return categoryProductRepository.save(existingCategoryProduct);
    }*/
   public CategoryProduct updateCategoryProduct(Long categoryId, CategoryProduct updatedCategoryProduct) {

       CategoryProduct existingCategoryProduct = categoryProductRepository.findById(categoryId)
               .orElseThrow(() -> new RuntimeException("CategoryProduct not found with id: " + categoryId)); //Find the existing category by its ID


       existingCategoryProduct.setCategoryName(updatedCategoryProduct.getCategoryName());//new values

       return categoryProductRepository.save(existingCategoryProduct);
   }

    @Override
    public void deleteCategoryProduct(Long idCategory) {

      // categoryProductRepository.deleteById(idCategory);
        CategoryProduct categoryProduct = getCategoryProductById(idCategory);
        List<SubCategoryProduct> subCategoryProducts = categoryProduct.getSubCategorys();
        for (SubCategoryProduct subCategoryProduct : subCategoryProducts) {
            deleteSubCategoryProduct(subCategoryProduct.getIdSubCategory());
        }
        categoryProductRepository.deleteById(idCategory);
    }
    //////////////////////////////////////SUBCATEGORY
    @Override
    public SubCategoryProduct createSubCategoryProduct(SubCategoryProduct subCategoryProduct) {
        return subCategoryProductRepository.save(subCategoryProduct);
    }

    @Override
    public List<SubCategoryProduct> getAllSubCategoryProducts() {
        return subCategoryProductRepository.findAll();
    }

    @Override
    public SubCategoryProduct getSubCategoryProductById(Long idSubCategory) {
        return subCategoryProductRepository.findById(idSubCategory).orElse(null);
    }

    @Override
   /* public SubCategoryProduct updateSubCategoryProduct(Long idSubCategory, SubCategoryProduct subCategoryProduct) {
        SubCategoryProduct existingSubCategoryProduct = getSubCategoryProductById(idSubCategory);
        existingSubCategoryProduct.setSubCategoryName(subCategoryProduct.getSubCategoryName());
        existingSubCategoryProduct.setCategory(subCategoryProduct.getCategory());
        existingSubCategoryProduct.setProducts(subCategoryProduct.getProducts());
        return subCategoryProductRepository.save(existingSubCategoryProduct);
    }*/
    /*public SubCategoryProduct updateSubCategoryProduct(Long subCategoryId, SubCategoryProduct updatedSubCategoryProduct) {
        SubCategoryProduct existingSubCategoryProduct = subCategoryProductRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategoryProduct not found with id: " + subCategoryId));

        existingSubCategoryProduct.setSubCategoryName(updatedSubCategoryProduct.getSubCategoryName());
        return subCategoryProductRepository.save(existingSubCategoryProduct);
    }*/
    public SubCategoryProduct updateSubCategoryProduct(Long subCategoryId, SubCategoryProduct updatedSubCategoryProduct) {
        SubCategoryProduct existingSubCategoryProduct = subCategoryProductRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategoryProduct not found with id: " + subCategoryId));

        if (updatedSubCategoryProduct.getSubCategoryName() != null) {
            existingSubCategoryProduct.setSubCategoryName(updatedSubCategoryProduct.getSubCategoryName());
        }

        return subCategoryProductRepository.save(existingSubCategoryProduct);
    }



    @Override
    public void deleteSubCategoryProduct(Long idSubCategory) {

        //subCategoryProductRepository.deleteById(idSubCategory);
        SubCategoryProduct subCategoryProduct = getSubCategoryProductById(idSubCategory);
        List<Product> products = subCategoryProduct.getProducts();
        for (Product product : products) {
            deleteProduct(product.getIdProduct());
        }
        subCategoryProductRepository.deleteById(idSubCategory);
    }
/////// affect subcategory to category
    @Override
    public void addSubcategoryToCategory(Long idCategory, Long idSubCategory) {
        CategoryProduct category = categoryProductRepository.findById(idCategory)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + idCategory));

        SubCategoryProduct subCategory = subCategoryProductRepository.findById(idSubCategory)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + idSubCategory));

        category.getSubCategorys().add(subCategory);
        subCategory.setCategory(category);

        categoryProductRepository.save(category);
        subCategoryProductRepository.save(subCategory);

    }



    //////////////////////////////PRODUCT
    @Override
    @Transactional
  /*  public Product addProduct(Product product) {
        SubCategoryProduct subCategoryProduct = getSubCategoryProductById(product.getSubcategory().getIdSubCategory());
        product.setSubcategory(subCategoryProduct);
        User user = userService.getUserById(product.getUser().getId());
        product.setUser(User.builder().build());
        List<WishList> wishlists = product.getWishlists();
        for (WishList wishList : wishlists) {
            wishList.getProducts().add(product);
            updateWishList(wishList.getIdList(), wishList);
        }
        List<PictureProduct> pictureProducts = product.getPictureProducts();
        for (PictureProduct pictureProduct : pictureProducts) {
            pictureProduct.setProduct(product);
            addPictureProduct(pictureProduct);
        }
        OrderProduct orderProduct = getOrderProductById(product.getOrderproduct().getIdOrder());
        product.setOrderproduct(orderProduct);
        return productRepository.save(product);
    }*/

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }


    @Override
    @Transactional
   /* public Product updateProduct(Long idProduct, Product product) {
        Product existingProduct = getProductById(idProduct);
        existingProduct.setName(product.getName());
        existingProduct.setTypeProduct(product.getTypeProduct());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setWeight(product.getWeight());
        existingProduct.setPrice(product.getPrice());
        SubCategoryProduct subCategoryProduct = getSubCategoryProductById(product.getSubcategory().getIdSubCategory());
        existingProduct.setSubcategory(subCategoryProduct);

        User user = userService.getUserById(product.getUser().getId());

        existingProduct.setUser(User.builder().build());// USER
        existingProduct.setWishlists(product.getWishlists());
        existingProduct.setOrderproduct(product.getOrderproduct());
        return productRepository.save(existingProduct);
    }*/
   /* public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setTypeProduct(updatedProduct.getTypeProduct());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setWeight(updatedProduct.getWeight());
        existingProduct.setPrice(updatedProduct.getPrice());
        return productRepository.save(existingProduct);

    }*/
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Update name if not null in the updated product
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }

        // Update typeProduct if not null in the updated product
        if (updatedProduct.getTypeProduct() != null) {
            existingProduct.setTypeProduct(updatedProduct.getTypeProduct());
        }

        // Update description if not null in the updated product
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }

        // Update quantity if not null in the updated product
        if (updatedProduct.getQuantity() != 0) {
            existingProduct.setQuantity(updatedProduct.getQuantity());
        }

        // Update weight if not null in the updated product
        if (updatedProduct.getWeight() != null) {
            existingProduct.setWeight(updatedProduct.getWeight());
        }

        // Update price if not null in the updated product
        if (updatedProduct.getPrice() != 0) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long idProduct) {
        Product product = getProductById(idProduct);
        List<PictureProduct> pictureProducts = product.getPictureProducts();
        for (PictureProduct pictureProduct : pictureProducts) {
           // deletePictureProduct(pictureProduct.getIdpicture());
           // deletePictureProduct(pictureProduct.getIdpicture());
        }
        productRepository.deleteById(idProduct);
    }
    @Override
    public Product getProductById(Long idProduct) {
        return productRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Product not found with id " + idProduct));
    }
////////////////////PRODUCTTOSUBCATEGORY
    @Override
    public void assignProductToSubCategory(Long idProduct, Long idSubCategory) {
        Product product = productRepository.findById(idProduct)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + idProduct));

        SubCategoryProduct subCategory = subCategoryProductRepository.findById(idSubCategory)
                .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + idSubCategory));

        product.setSubcategory(subCategory);
        productRepository.save(product);

    }

    @Override
    public List<Product> getAllProductsSortedByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    @Override
    public List<Product> getProductsSortedByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }
/////////////////////////////
   /* @Override
    public void addToFavorites(User user, Product product) {
        if (!isProductInFavorites(user, product)) {
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            FavoriteProduct favoriteProduct = new FavoriteProduct(user, productList);
            favoriteProductRepository.save(favoriteProduct);
        }
    }*/
@Override
public void addToFavorites(Product product) {
    // Récupérer l'utilisateur avec l'ID 1
    User user = userService.getUserById(1L);
    if (user == null) {
        // Gérer le cas où l'utilisateur avec l'ID 1 n'est pas trouvé
        // Peut-être lancer une exception ou enregistrer un message d'erreur
        return;
    }

    if (!isProductInFavorites( product)) {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        FavoriteProduct favoriteProduct = new FavoriteProduct(user, productList);
        favoriteProductRepository.save(favoriteProduct);
    }
}


   /* @Override
    public void removeFromFavorites(User user, Product product) {
        FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserAndProducts(user, product);
        if (favoriteProduct != null) {
            favoriteProductRepository.delete(favoriteProduct);
        }
    }*/
   @Override
   public void removeFromFavorites(Product product) {
       Long id = 1L; // Définition de l'ID utilisateur statique à 1
       User user = userRepository.findById(id).orElse(null);
       if (user != null) {
           FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserAndProducts(user, product);
           if (favoriteProduct != null) {
               favoriteProductRepository.delete(favoriteProduct);
           }
       }
   }


    @Override
    public List<Product> getUserFavorites(User user) {
        List<FavoriteProduct> favoriteProducts = favoriteProductRepository.findByUser(user);
        List<Product> products = new ArrayList<>();
        for (FavoriteProduct favoriteProduct : favoriteProducts) {
            products.addAll(favoriteProduct.getProducts());
        }
        return products;
    }


   /* @Override
    public boolean isProductInFavorites(User user, Product product) {
        FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserAndProducts(user, product);
        return favoriteProduct != null;
    }*/
   @Override
   public boolean isProductInFavorites(Product product) {
       Long id = 1L; // Définir l'ID de l'utilisateur statiquement
       User staticUser = userService.getUserById(id);
       if (staticUser == null) {
           // Gérer le cas où l'utilisateur statique n'est pas trouvé
           return false;
       }
       FavoriteProduct favoriteProduct = favoriteProductRepository.findByUserAndProducts(staticUser, product);
       return favoriteProduct != null;
   }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }



    ////////////////////PICTURE
    //hedhom zeda ykhdmo
    /*@Override
    public PictureProduct addPictureProduct(PictureProduct pictureProduct) {
       zeyd satrin hdhom Product product = getProductById(pictureProduct.getProduct().getIdProduct());
        pictureProduct.setProduct(product);
       return pictureProductRepository.save(pictureProduct);
    }*/

    //@Override
   /* public PictureProduct updatePictureProduct(Long idpicture, PictureProduct pictureProduct) {
        PictureProduct existingPictureProduct = getPictureProductById(idpicture);
        existingPictureProduct.setDateAdded(pictureProduct.getDateAdded());
        existingPictureProduct.setFormat(pictureProduct.getFormat());
        existingPictureProduct.setPath(pictureProduct.getPath());
        Product product = getProductById(pictureProduct.getProduct().getIdProduct());
        existingPictureProduct.setProduct(product);
        return pictureProductRepository.save(existingPictureProduct);
    }*/
  /* public PictureProduct updatePictureProduct(Long pictureId, PictureProduct updatedPictureProduct) {
       PictureProduct existingPictureProduct = pictureProductRepository.findById(pictureId)
               .orElseThrow(() -> new RuntimeException("Picture product not found with id: " + pictureId));

       existingPictureProduct.setDateAdded(updatedPictureProduct.getDateAdded());
       existingPictureProduct.setFormat(updatedPictureProduct.getFormat());
       existingPictureProduct.setPath(updatedPictureProduct.getPath());
       return pictureProductRepository.save(existingPictureProduct);
   }*/
    //hedhy nahytha o tmshy
   /* public PictureProduct updatePictureProduct(Long pictureId, PictureProduct updatedPictureProduct) {
        PictureProduct existingPictureProduct = pictureProductRepository.findById(pictureId)
                .orElseThrow(() -> new RuntimeException("Picture product not found with id: " + pictureId));

        // Update attributes if not null in the updatedPictureProduct
        if (updatedPictureProduct.getDateAdded() != null) {
            existingPictureProduct.setDateAdded(updatedPictureProduct.getDateAdded());
        }
        if (updatedPictureProduct.getFormat() != null) {
            existingPictureProduct.setFormat(updatedPictureProduct.getFormat());
        }
        if (updatedPictureProduct.getPath() != null) {
            existingPictureProduct.setPath(updatedPictureProduct.getPath());
        }

        return pictureProductRepository.save(existingPictureProduct);
    }


    @Override
    public void deletePictureProduct(Long idpicture) {
        PictureProduct pictureProduct = getPictureProductById(idpicture);
        Path path = Paths.get(pictureProduct.getPath());
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pictureProductRepository.deleteById(idpicture);
    }

    @Override
    public List<PictureProduct> getAllPictureProducts() {
        return pictureProductRepository.findAll();
    }

    @Override
    public PictureProduct getPictureProductById(Long idpicture) {
        return pictureProductRepository.findById(idpicture).orElse(null);
    }*/

    //////////////////////ORDER
    /*@Override
    @Transactional
    public OrderProduct addOrderProduct(OrderProduct orderProduct) {
       PaymentProduct paymentProduct = paymentProductService.getPaymentProductById(orderProduct.getPaymentproduct().getIdPayment());
        orderProduct.setPaymentproduct(paymentProduct); // paymenent
        List<Product> products = orderProduct.getProducts();
        for (Product product : products) {
            product.setOrderproduct(orderProduct);
            updateProduct(product.getIdProduct(), product);
        }
        return orderProductRepository.save(orderProduct);
    }*/

    ////tajrba jdidaaa
    @Override
    @Transactional
    public List<PictureProduct> list(){
        return pictureProductRepository.findByOrderByIdpicture();
    }
    @Override
    @Transactional
    public PictureProduct getLastImage() {
        return pictureProductRepository.findFirstByOrderByIdpictureDesc();
    }


    @Override
    @Transactional

    public void save(List<PictureProduct> pictureProducts) {

        // Vérifiez d'abord si la liste n'est pas vide et contient un seul élément
        if (pictureProducts != null && pictureProducts.size() == 1) {
            // Enregistrez le seul élément dans la liste
            pictureProductRepository.save(pictureProducts.get(0));
        } else {
            throw new IllegalArgumentException("La liste doit contenir exactement un élément.");
        }
    }

    @Transactional
    @Override

    public Optional<PictureProduct> getOne(Long idpicture){
        return pictureProductRepository.findById(idpicture);
    }
    @Transactional
    @Override
    public void save(PictureProduct pictureProduct){
        pictureProductRepository.save(pictureProduct);
    }
    @Transactional
    @Override
    public void delete(Long idpicture){
        pictureProductRepository.deleteById(idpicture);
    }
    @Transactional
    @Override
    public  boolean exists(Long idpicture){
        return pictureProductRepository.existsById(idpicture);
    }

    @Override
    public void assignProductPictureToProduct(Long idProduct, Long idpicture) {
        // Récupérer les instances de Product et PictureProduct à partir de leurs identifiants
        Product product = productRepository.findById(idProduct).orElseThrow(() -> new RuntimeException("Product not found"));
        PictureProduct pictureProduct = pictureProductRepository.findById(idpicture).orElseThrow(() -> new RuntimeException("PictureProduct not found"));

        // Associer PictureProduct à Product
        pictureProduct.setProduct(product);
        product.getPictureProducts().add(pictureProduct);

        // Enregistrer les modifications
        productRepository.save(product);
    }

    @Override
    public Long getLastProductId() {
        return productRepository.getLastProductId();
    }

    @Override
    public Long getLastPictureId() {
        return pictureProductRepository.getLastPictureId();
    }
    @Override
    public String getImageUrlForProduct(Long idProduct) {
        Optional<Product> productOptional = productRepository.findById(idProduct);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getPictureProducts() != null && !product.getPictureProducts().isEmpty()) {
                PictureProduct pictureProduct = product.getPictureProducts().get(0); // Assuming there's only one picture per product
                return pictureProduct.getImageUrl();
            } else {
                return "No image found for the product";
            }
        } else {
            return "Product not found";
        }
    }


    @Transactional
    @Override
    public OrderProduct addOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    @Transactional
    /*public OrderProduct updateOrderProduct(Long idOrder, OrderProduct orderProduct) {
        OrderProduct existingOrderProduct = getOrderProductById(idOrder);
        existingOrderProduct.setQuantity(orderProduct.getQuantity());
        existingOrderProduct.setAmount(orderProduct.getAmount());
        existingOrderProduct.setAddress(orderProduct.getAddress());
        existingOrderProduct.setPostcode(orderProduct.getPostcode());
        existingOrderProduct.setCity(orderProduct.getCity());
        existingOrderProduct.setNotes(orderProduct.getNotes());
        PaymentProduct paymentProduct = paymentProductService.getPaymentProductById(orderProduct.getPaymentproduct().getIdPayment());
        existingOrderProduct.setPaymentproduct(paymentProduct);
        List<Product> products = orderProduct.getProducts();
        for (Product product : products) {
            product.setOrderproduct(existingOrderProduct);
            updateProduct(product.getIdProduct(), product);
        }
        return orderProductRepository.save(existingOrderProduct);
    }*/
  /* public OrderProduct updateOrderProduct(Long orderId, OrderProduct updatedOrderProduct) {

       OrderProduct existingOrderProduct = orderProductRepository.findById(orderId)
               .orElseThrow(() -> new RuntimeException("Order product not found with id: " + orderId));

       existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
       existingOrderProduct.setAmount(updatedOrderProduct.getAmount());
       existingOrderProduct.setAddress(updatedOrderProduct.getAddress());
       existingOrderProduct.setPostcode(updatedOrderProduct.getPostcode());
       existingOrderProduct.setCity(updatedOrderProduct.getCity());
       existingOrderProduct.setNotes(updatedOrderProduct.getNotes());

       return orderProductRepository.save(existingOrderProduct);
   }*/
    public OrderProduct updateOrderProduct(Long orderId, OrderProduct updatedOrderProduct) {
        OrderProduct existingOrderProduct = orderProductRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order product not found with id: " + orderId));

        // Update attributes if not null in the updatedOrderProduct
        if (updatedOrderProduct.getQuantity() != 0) {
            existingOrderProduct.setQuantity(updatedOrderProduct.getQuantity());
        }
        if (updatedOrderProduct.getAmount() != 0) {
            existingOrderProduct.setAmount(updatedOrderProduct.getAmount());
        }
        if (updatedOrderProduct.getAddress() != null) {
            existingOrderProduct.setAddress(updatedOrderProduct.getAddress());
        }
        if (updatedOrderProduct.getPostcode() != 0) {
            existingOrderProduct.setPostcode(updatedOrderProduct.getPostcode());
        }
        if (updatedOrderProduct.getCity() != null) {
            existingOrderProduct.setCity(updatedOrderProduct.getCity());
        }
        if (updatedOrderProduct.getNotes() != null) {
            existingOrderProduct.setNotes(updatedOrderProduct.getNotes());
        }

        return orderProductRepository.save(existingOrderProduct);
    }


    @Override
    @Transactional
    public void deleteOrderProduct(Long idOrder) {
        OrderProduct orderProduct = getOrderProductById(idOrder);
        List<Product> products = orderProduct.getProducts();
        for (Product product : products) {
            product.setOrderproduct(null);
            updateProduct(product.getIdProduct(), product);
        }
        orderProductRepository.deleteById(idOrder);
    }

    @Override
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    @Override
    public OrderProduct getOrderProductById(Long idOrder) {
        return orderProductRepository.findById(idOrder).orElseThrow(() -> new RuntimeException("OrderProduct not found with id " + idOrder));
    }
    //////////////////WISHLIST
    @Override
    @Transactional
    public WishList addWishList(WishList wishList) {
        /*User user = getUserById(wishList.getUser().getIdUser());
        wishList.setUser(user); ///////USSSEERR
        List<Product> products = wishList.getProducts();
        for (Product product : products) {
            product.getWishlists().add(wishList);
            updateProduct(product.getIdProduct(), product);
        }*/
        return wishListRepository.save(wishList);
    }

    @Override
    @Transactional
   /* public WishList updateWishList(Long idList, WishList wishList) {
        WishList existingWishList = getWishListById(idList);
        existingWishList.setProducts(wishList.getProducts());
        User user = userService.getUserById(wishList.getUser().getIdUser());
        existingWishList.setUser(user);
        List<Product> products = wishList.getProducts();
        for (Product product : products) {
            product.getWishlists().add(existingWishList);
            updateProduct(product.getIdProduct(), product);
        }
        return wishListRepository.save(existingWishList);
    }*/
   /* public WishList updateWishList(Long wishListId, WishList updatedWishList) {

        WishList existingWishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new RuntimeException("Wish list not found with id: " + wishListId));


        existingWishList.setWishname(updatedWishList.getWishname());
        existingWishList.setProducts(updatedWishList.getProducts());

        return wishListRepository.save(existingWishList);
    }*/
    public WishList updateWishList(Long wishListId, WishList updatedWishList) {
        WishList existingWishList = wishListRepository.findById(wishListId)
                .orElseThrow(() -> new RuntimeException("Wish list not found with id: " + wishListId));

        // Update attributes if not null in the updatedWishList
        if (updatedWishList.getWishname() != null) {
            existingWishList.setWishname(updatedWishList.getWishname());
        }
        if (updatedWishList.getProducts() != null) {
            existingWishList.setProducts(updatedWishList.getProducts());
        }

        return wishListRepository.save(existingWishList);
    }

    @Override
    @Transactional
    public void deleteWishList(Long idList) {
        WishList wishList = getWishListById(idList);
        List<Product> products = wishList.getProducts();
        for (Product product : products) {
            product.getWishlists().remove(wishList);
            updateProduct(product.getIdProduct(), product);
        }
        wishListRepository.deleteById(idList);
    }

    @Override
    public List<WishList> getAllWishLists() {
        return wishListRepository.findAll();
    }

    @Override
    public WishList getWishListById(Long idList) {
        return wishListRepository.findById(idList).orElseThrow(() -> new RuntimeException("WishList not found with id " + idList));
    }







}
