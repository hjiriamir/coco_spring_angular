package tn.esprit.coco.serviceImp;

import tn.esprit.coco.entity.*;

import java.util.List;
import java.util.Optional;

public interface MarketPlaceImpl {
    /////////CATEGORY
    CategoryProduct createCategoryProduct(CategoryProduct categoryProduct);
    List<CategoryProduct> getAllCategoryProducts();
    CategoryProduct getCategoryProductById(Long idCategory);
    CategoryProduct updateCategoryProduct(Long idCategory, CategoryProduct categoryProduct);
    void deleteCategoryProduct(Long idCategory);
    /////////////////SUBCATEGORY
    SubCategoryProduct createSubCategoryProduct(SubCategoryProduct subCategoryProduct);
    List<SubCategoryProduct> getAllSubCategoryProducts();
    SubCategoryProduct getSubCategoryProductById(Long idSubCategory);
    SubCategoryProduct updateSubCategoryProduct(Long idSubCategory, SubCategoryProduct subCategoryProduct);
    void deleteSubCategoryProduct(Long idSubCategory);
    /////////////AFFECTATIONCATEGORYTOSUBCATEGORY
    void addSubcategoryToCategory(Long categoryId, Long subcategoryId);

    ///////////PICTURE
   /*PictureProduct addPictureProduct(PictureProduct pictureProduct);
    PictureProduct updatePictureProduct(Long idpicture, PictureProduct pictureProduct);
    void deletePictureProduct(Long idpicture);
    List<PictureProduct> getAllPictureProducts();
    PictureProduct getPictureProductById(Long idpicture);*/
     List<PictureProduct> list();
     void save(List<PictureProduct> pictureProducts) ;

        Optional<PictureProduct> getOne(Long idpicture);
    void save(PictureProduct pictureProduct);
     void delete (Long idpicture);
     boolean exists(Long idpicture);
    public PictureProduct getLastImage();
    void assignProductPictureToProduct(Long idProduct, Long idpicture) ;
    Long getLastProductId();

    Long getLastPictureId();
    String getImageUrlForProduct(Long idProduct);
    /////////////PRODUCt

    Product addProduct(Product product);
    Product updateProduct(Long idProduct, Product product);
    void deleteProduct(Long idProduct);
    List<Product> getAllProducts();
    Product getProductById(Long idProduct);
    ////ASSIGNPRODUCTTOSUBCATEGORY
     void assignProductToSubCategory(Long idProduct, Long idSubCategory);
    List<Product> getAllProductsSortedByPriceAsc();

    List<Product> getProductsSortedByPriceDesc();
    //void addToFavorites(User user, Product product);
    void addToFavorites(Product product);
   // void removeFromFavorites(User user, Product product);
   void removeFromFavorites(Product product);
    List<Product> getUserFavorites(User user);
    boolean isProductInFavorites(Product product);


        //////////MODULE2

    /////////ORDER

    OrderProduct addOrderProduct(OrderProduct orderProduct);
    OrderProduct updateOrderProduct(Long idOrder, OrderProduct orderProduct);
    void deleteOrderProduct(Long idOrder);
    List<OrderProduct> getAllOrderProducts();
    OrderProduct getOrderProductById(Long idOrder);
    //////WISHLIST
    WishList addWishList(WishList wishList);
    WishList updateWishList(Long idOrder, WishList wishList);
    void deleteWishList(Long idOrder);
    List<WishList> getAllWishLists();
    WishList getWishListById(Long idOrder);



}
