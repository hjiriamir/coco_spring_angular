package tn.esprit.coco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.service.CloudinaryService;
import tn.esprit.coco.service.ImageUploadImpl;
import tn.esprit.coco.service.MarketPlaceService;
import tn.esprit.coco.service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
//@RequestMapping("/MarketPlace")
public class MarketPlaceController {
    @Autowired
    private MarketPlaceService marketPlaceService;
    @Autowired
    private final ImageUploadImpl imageUpload;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    UserService userService;

    // @RequestMapping("/")
   /* public String home(){
        return "home";
    }
    @PostMapping("/uploadmeth1")
    public  String uploadFile(@RequestParam("image")MultipartFile multipartFile, Model model) throws IOException {
       String imageURL = imageUpload.uploadFile(multipartFile);
       model.addAttribute("imageURL",imageURL);
       return "gallery";
    }*/
    @GetMapping("/list/pic")
    public ResponseEntity<List<PictureProduct>> list() {
        List<PictureProduct> list = marketPlaceService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/lastImage")
    public ResponseEntity<PictureProduct> getLastImage() {
        PictureProduct lastImage = marketPlaceService.getLastImage();
        if (lastImage != null) {
            return new ResponseEntity<>(lastImage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/saveone")
    public ResponseEntity<?> savePictureProducts(@RequestBody List<PictureProduct> pictureProducts) {
        try {
            marketPlaceService.save(pictureProducts);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non validé!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        PictureProduct pictureProduct = new PictureProduct((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        marketPlaceService.save(pictureProduct);
        return new ResponseEntity<>("image ajoutée avec succès : ", HttpStatus.OK);
    }

    @DeleteMapping("/deletePic/{idpicture}")
    public ResponseEntity<String> delete(@PathVariable("idpicture") Long idpicture) {
        Optional<PictureProduct> imageOptional = marketPlaceService.getOne(idpicture);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("n existe pas", HttpStatus.NOT_FOUND);
        }
        PictureProduct pictureProduct = imageOptional.get();
        String cloudinaryImageId = pictureProduct.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
            marketPlaceService.delete(idpicture);
            return ResponseEntity.ok().body("Image supprimée avec succès.");
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete from cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/assignPictureToProduct")
    public void assignPictureToProduct(@RequestParam Long idProduct, @RequestParam Long idpicture) {
        marketPlaceService.assignProductPictureToProduct(idProduct, idpicture);
    }


    @PostMapping("/AddCategoryProduct")
    public CategoryProduct createCategoryProduct(@RequestBody CategoryProduct categoryProduct) {
        return marketPlaceService.createCategoryProduct(categoryProduct);
    }

    @GetMapping("/GetCategoryProduct")
    public List<CategoryProduct> getAllCategoryProducts() {
        return marketPlaceService.getAllCategoryProducts();
    }

    @GetMapping("/CategoryProduct/{idCategory}")
    public CategoryProduct getCategoryProductById(@PathVariable Long idCategory) {
        return marketPlaceService.getCategoryProductById(idCategory);
    }

    @PutMapping("/CategoryProduct/{idCategory}")
    public CategoryProduct updateCategoryProduct(@PathVariable Long idCategory, @RequestBody CategoryProduct categoryProduct) {
        return marketPlaceService.updateCategoryProduct(idCategory, categoryProduct);
    }
  /* @PutMapping("/updateCategoryProduct")
   public CategoryProduct updateCategoryProduct(@RequestBody CategoryProduct categoryProduct) {
       return  marketPlaceService.createCategoryProduct(categoryProduct);
   }*/

    @DeleteMapping("/CategoryProduct/{idCategory}")
    public void deleteCategoryProduct(@PathVariable Long idCategory) {
        marketPlaceService.deleteCategoryProduct(idCategory);
    }

    /*@DeleteMapping("/CategoryProduct/{idCategory}")
    public ResponseEntity<Void> deleteCategoryProduct(@PathVariable Long idCategory) {x
        try {
            marketPlaceService.deleteCategoryProduct(idCategory);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
   /* @GetMapping("/CategoryProduct/name/{categoryName}")
    public ResponseEntity<CategoryProduct> getCategoryProductByName(@PathVariable String categoryName) {
        CategoryProduct categoryProduct = marketPlaceService.getCategoryProductByName(categoryName);
        if (categoryProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryProduct, HttpStatus.OK);
    }*/
    /////subcategory
    @PostMapping("/AddSubCategory")
    public SubCategoryProduct createSubCategoryProduct(@RequestBody SubCategoryProduct subCategoryProduct) {
        return marketPlaceService.createSubCategoryProduct(subCategoryProduct);
    }

    @GetMapping("/getSubCategoryProduct")
    public List<SubCategoryProduct> getAllSubCategoryProducts() {
        return marketPlaceService.getAllSubCategoryProducts();
    }

    @GetMapping("/SubCategoryProduct/{idSubCategory}")
    public SubCategoryProduct getSubCategoryProductById(@PathVariable Long idSubCategory) {
        return marketPlaceService.getSubCategoryProductById(idSubCategory);
    }

    @PutMapping("/SubCategoryProduct/{idSubCategory}")
    public SubCategoryProduct updateSubCategoryProduct(@PathVariable Long idSubCategory, @RequestBody SubCategoryProduct subCategoryProduct) {
        return marketPlaceService.updateSubCategoryProduct(idSubCategory, subCategoryProduct);
    }

    @DeleteMapping("/SubCategoryProduct/{idSubCategory}")
    public void deleteSubCategoryProduct(@PathVariable Long idSubCategory) {
        marketPlaceService.deleteSubCategoryProduct(idSubCategory);
    }

    ///////product
    @PostMapping("/AddProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product addedProduct = marketPlaceService.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }
    @GetMapping("/last-id/picture")
    public ResponseEntity<Long> getLastPictureId() {
        Long lastPictureId = marketPlaceService.getLastPictureId();
        return ResponseEntity.status(HttpStatus.OK).body(lastPictureId);
    }
    @GetMapping("/last-id/product")
    public ResponseEntity<Long> getLastProductId() {
        Long lastProductId = marketPlaceService.getLastProductId();
        return ResponseEntity.status(HttpStatus.OK).body(lastProductId);
    }
    @GetMapping("/imageurl/{idProduct}")
    public ResponseEntity<String> getImageUrlForProduct(@PathVariable("idProduct") Long idProduct) {
        String imageUrl = marketPlaceService.getImageUrlForProduct(idProduct);
        if (imageUrl != null) {

            return ResponseEntity.ok(imageUrl);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/Product/{idProduct}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long idProduct, @RequestBody Product product) {
        Product updatedProduct = marketPlaceService.updateProduct(idProduct, product);
        if (updatedProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/Productdelete/{idProduct}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long idProduct) {
        marketPlaceService.deleteProduct(idProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Productget/{idProduct}")
    public ResponseEntity<Product> getProductById(@PathVariable Long idProduct) {
        Product product = marketPlaceService.getProductById(idProduct);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/Product")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = marketPlaceService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/ProductsortedByPriceAsc")
    public ResponseEntity<List<Product>> getProductsSortedByPriceAsc() {
        List<Product> products = marketPlaceService.getAllProductsSortedByPriceAsc();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/ProdSortedByPriceDesc")
    public ResponseEntity<List<Product>> getProductsSortedByPriceDesc() {
        List<Product> products = marketPlaceService.getProductsSortedByPriceDesc();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    ////FAV PROD
    @GetMapping("getuserid/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

   /* @PostMapping("/addFavProd")
    public ResponseEntity<String> addToFavorites(@RequestParam Long id, @RequestParam Long idProduct) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Product product = marketPlaceService.getProductById(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        marketPlaceService.addToFavorites(user, product);
        return ResponseEntity.ok().body("Product added to favorites successfully");
    }*/
   @PostMapping("/addFavProd")
   public ResponseEntity<String> addToFavorites(@RequestParam Long idProduct) {
       // Utilisateur statique avec l'ID 1
       User user = userService.getUserById(1L);
       if (user == null) {
           return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
       }

       Product product = marketPlaceService.getProductById(idProduct);
       if (product == null) {
           return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
       }

       marketPlaceService.addToFavorites(product);
       return ResponseEntity.ok().body("Product added to favorites successfully");
   }
    @DeleteMapping("/removeFavProd")
    public ResponseEntity<String> removeFromFavorites(@RequestParam Long idProduct) {
        Long id = 1L; // Définition de l'ID utilisateur statique à 1
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Product product = marketPlaceService.getProductById(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        marketPlaceService.removeFromFavorites( product);
        return ResponseEntity.ok().body("Product removed from favorites successfully");
    }

  /*  @DeleteMapping("/removeFavProd")
    public ResponseEntity<String> removeFromFavorites(@RequestParam Long id, @RequestParam Long idProduct) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        Product product = marketPlaceService.getProductById(idProduct);
        if (product == null) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        marketPlaceService.removeFromFavorites(user, product);
        return ResponseEntity.ok().body("Product removed from favorites successfully");
    }*/


    @GetMapping("getfav/{id}")
    public ResponseEntity<List<Product>> getUserFavorites(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Product> favorites = marketPlaceService.getUserFavorites(user);
        return ResponseEntity.ok().body(favorites);
    }

   /* @GetMapping("FavProd/check")
    public ResponseEntity<Boolean> isProductInFavorites(@RequestParam Long id, @RequestParam Long idProduct) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        Product product = marketPlaceService.getProductById(idProduct);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
        boolean isInFavorites = marketPlaceService.isProductInFavorites(user, product);
        return ResponseEntity.ok().body(isInFavorites);
    }*/
   @GetMapping("FavProd/check")
   public ResponseEntity<Boolean> isProductInFavorites(@RequestParam Long idProduct) {
       Long id = 1L; // ID utilisateur statique égal à 1
       User user = userService.getUserById(id);
       if (user == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
       }
       Product product = marketPlaceService.getProductById(idProduct);
       if (product == null) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
       }
       boolean isInFavorites = marketPlaceService.isProductInFavorites(product);
       return ResponseEntity.ok().body(isInFavorites);
   }







    ////////////////Picture
   /* @PostMapping("/Picture")
    public ResponseEntity<PictureProduct> addPictureProduct(@RequestBody PictureProduct pictureProduct) {
        PictureProduct addedPictureProduct = marketPlaceService.addPictureProduct(pictureProduct);
        return new ResponseEntity<>(addedPictureProduct, HttpStatus.CREATED);
    }

    @PutMapping("/Picture/{idpicture}")
    public ResponseEntity<PictureProduct> updatePictureProduct(@PathVariable Long idpicture, @RequestBody PictureProduct pictureProduct) {
        PictureProduct updatedPictureProduct = marketPlaceService.updatePictureProduct(idpicture, pictureProduct);
        if (updatedPictureProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPictureProduct, HttpStatus.OK);
    }

    @DeleteMapping("/Picture/{idpicture}")
    public ResponseEntity<Void> deletePictureProduct(@PathVariable Long idpicture) {
        marketPlaceService.deletePictureProduct(idpicture);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Picture/{idpicture}")
    public ResponseEntity<PictureProduct> getPictureProductById(@PathVariable Long idpicture) {
        PictureProduct pictureProduct = marketPlaceService.getPictureProductById(idpicture);
        if (pictureProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pictureProduct, HttpStatus.OK);
    }

    @GetMapping("/Picture")
    public ResponseEntity<List<PictureProduct>> getAllPictureProducts() {
        List<PictureProduct> pictureProducts = marketPlaceService.getAllPictureProducts();
        return new ResponseEntity<>(pictureProducts, HttpStatus.OK);
    }*/
    /////ORDER
    @PostMapping("/Order")
    public ResponseEntity<OrderProduct> addOrderProduct(@RequestBody OrderProduct orderProduct) {
        OrderProduct addedOrderProduct = marketPlaceService.addOrderProduct(orderProduct);
        return new ResponseEntity<>(addedOrderProduct, HttpStatus.CREATED);
    }

    @PutMapping("/Order/{idOrder}")
    public ResponseEntity<OrderProduct> updateOrderProduct(@PathVariable Long idOrder, @RequestBody OrderProduct orderProduct) {
        OrderProduct updatedOrderProduct = marketPlaceService.updateOrderProduct(idOrder, orderProduct);
        if (updatedOrderProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedOrderProduct, HttpStatus.OK);
    }

    @DeleteMapping("/Order/{idOrder}")
    public ResponseEntity<Void> deleteOrderProduct(@PathVariable Long idOrder) {
        marketPlaceService.deleteOrderProduct(idOrder);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/Order/{idOrder}")
    public ResponseEntity<OrderProduct> getOrderProductById(@PathVariable Long idOrder) {
        OrderProduct orderProduct = marketPlaceService.getOrderProductById(idOrder);
        if (orderProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderProduct, HttpStatus.OK);
    }

    @GetMapping("/Order")
    public ResponseEntity<List<OrderProduct>> getAllOrderProducts() {
        List<OrderProduct> orderProducts = marketPlaceService.getAllOrderProducts();
        return new ResponseEntity<>(orderProducts, HttpStatus.OK);
    }
    //////WishList
    @PostMapping("/WishList")
    public ResponseEntity<WishList> addWishList(@RequestBody WishList wishList) {
        WishList addedWishList = marketPlaceService.addWishList(wishList);
        return new ResponseEntity<>(addedWishList, HttpStatus.CREATED);
    }

    @PutMapping("/WishList/{idList}")
    public ResponseEntity<WishList> updateWishList(@PathVariable Long idList, @RequestBody WishList wishList) {
        WishList updatedWishList = marketPlaceService.updateWishList(idList, wishList);
        if (updatedWishList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedWishList, HttpStatus.OK);
    }

    @DeleteMapping("/WishList/{idList}")
    public ResponseEntity<Void> deleteWishList(@PathVariable Long idList) {
        marketPlaceService.deleteWishList(idList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/WishList/{idList}")
    public ResponseEntity<WishList> getWishListById(@PathVariable Long idList) {
        WishList wishList = marketPlaceService.getWishListById(idList);
        if (wishList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wishList, HttpStatus.OK);
    }

    @GetMapping("/WishList")
    public ResponseEntity<List<WishList>> getAllWishLists() {
        List<WishList> wishLists = marketPlaceService.getAllWishLists();
        return new ResponseEntity<>(wishLists, HttpStatus.OK);
    }

    @PutMapping("/{idCategory}/subcategories/{idSubCategory}")
    public ResponseEntity<String> addSubcategoryToCategory(@PathVariable Long idCategory, @PathVariable Long idSubCategory) {
        marketPlaceService.addSubcategoryToCategory(idCategory, idSubCategory);
        return ResponseEntity.ok("Subcategory added to category successfully.");
    }

    @PutMapping("/{idProduct}/subcategory/{idSubCategory}")
    public ResponseEntity<String> assignProductToSubCategory(@PathVariable Long idProduct, @PathVariable Long idSubCategory) {
        marketPlaceService.assignProductToSubCategory(idProduct, idSubCategory);
        return ResponseEntity.status(HttpStatus.OK).body("Product assigned to Subcategory successfully");
    }


}
