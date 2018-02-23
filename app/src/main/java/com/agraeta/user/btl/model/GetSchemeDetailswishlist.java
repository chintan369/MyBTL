package com.agraeta.user.btl.model;

import com.agraeta.user.btl.model.coupons.SchemeDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NWSPL6 on 2/22/2018.
 */

public class GetSchemeDetailswishlist extends AppModel {


    SchemeData data=new SchemeData();


    public SchemeData getData() {
        return data;
    }

    public class SchemeData
    {

        SchemeDetail Scheme=new SchemeDetail();

        SchemeGetProduct GetProduct=new SchemeGetProduct();


        public SchemeGetProduct getGetProduct() {
            return GetProduct;
        }

        public SchemeDetail getScheme() {
            return Scheme;
        }
    }

    public  class SchemeDetail{

      String  id="";
      String scheme_name="";
      String buy_prod_id="";
        String buy_prod_qty="";
        String get_prod_id="";
        String get_prod_qty="";
        String max_qty="";
        String date_start="";
        String date_end="";
        String type_id="";
        String discount_percentage="";
        String  status="";
        String scheme_title="";
        String category_id="";
        String scheme_parent_id="";
        String banner_img="";
        String flag_for_banner="";
        String created="";
        String modified="";


        public String getId() {
            return id;
        }

        public String getScheme_name() {
            return scheme_name;
        }

        public String getBuy_prod_id() {
            return buy_prod_id;
        }

        public String getBuy_prod_qty() {
            return buy_prod_qty;
        }

        public String getGet_prod_id() {
            return get_prod_id;
        }

        public String getGet_prod_qty() {
            return get_prod_qty;
        }

        public String getMax_qty() {
            return max_qty;
        }

        public String getDate_start() {
            return date_start;
        }

        public String getDate_end() {
            return date_end;
        }

        public String getType_id() {
            return type_id;
        }

        public String getDiscount_percentage() {
            return discount_percentage;
        }

        public String getStatus() {
            return status;
        }

        public String getScheme_title() {
            return scheme_title;
        }

        public String getCategory_id() {
            return category_id;
        }

        public String getScheme_parent_id() {
            return scheme_parent_id;
        }

        public String getBanner_img() {
            return banner_img;
        }

        public String getFlag_for_banner() {
            return flag_for_banner;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }
    }


    public class SchemeGetProduct
    {

        List<Productoptionclass>ProductOption=new ArrayList<>();


        String id="";
        String category_id="";
        String product_code="";
        String product_name="";
        String label_id="";
        String qty="";
        String mrp="";
        String dealer_price="";
        String distributor_price="";
        String professional_price="";
        String carpenter_price="";
        String description="";
        String specification="";
        String technical="";
        String catalogs="";
        String guarantee="";
        String videos="";
        String general="";
        String weight="";
        String meta_title="";
        String meta_keyword="";
        String meta_description="";
        String seo_url="";
        String sort_order="";
        String image="";
        String status="";
        String is_child_product="";
        String parent_product_code="";
        String is_tally_item="";
        String pack_of_qty="";
        String created="";
        String modified="";
        String selling_price="";

        public String getId() {
            return id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getLabel_id() {
            return label_id;
        }

        public String getQty() {
            return qty;
        }

        public String getMrp() {
            return mrp;
        }

        public String getDealer_price() {
            return dealer_price;
        }

        public String getDistributor_price() {
            return distributor_price;
        }

        public String getProfessional_price() {
            return professional_price;
        }

        public String getCarpenter_price() {
            return carpenter_price;
        }

        public String getDescription() {
            return description;
        }

        public String getSpecification() {
            return specification;
        }

        public String getTechnical() {
            return technical;
        }

        public String getCatalogs() {
            return catalogs;
        }

        public String getGuarantee() {
            return guarantee;
        }

        public String getVideos() {
            return videos;
        }

        public String getGeneral() {
            return general;
        }

        public String getWeight() {
            return weight;
        }

        public String getMeta_title() {
            return meta_title;
        }

        public String getMeta_keyword() {
            return meta_keyword;
        }

        public String getMeta_description() {
            return meta_description;
        }

        public String getSeo_url() {
            return seo_url;
        }

        public String getSort_order() {
            return sort_order;
        }

        public String getImage() {
            return image;
        }

        public String getStatus() {
            return status;
        }

        public String getIs_child_product() {
            return is_child_product;
        }

        public String getParent_product_code() {
            return parent_product_code;
        }

        public String getIs_tally_item() {
            return is_tally_item;
        }

        public String getPack_of_qty() {
            return pack_of_qty;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public List<Productoptionclass> getProductOption() {
            return ProductOption;
        }


        SchemeLabel Label=new SchemeLabel();

        public SchemeLabel getLabel() {
            return Label;
        }



    }

    public class SchemeLabel
    {

        String id="";
        String name="";
        String sort_order="";
        String status="";
        String created="";
        String modified="";


        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSort_order() {
            return sort_order;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }
    }

    public class  Productoptionclass{

        Option_class Option=new Option_class();
        Option_value_class OptionValue=new Option_value_class();
        List<Productoptionclass> Scheme=new ArrayList();

        String id="";
        String product_id="";
        String option_id="";
        String option_value_id="";
        String product_code="";
        String mrp="";
        String selling_price="";
        String dealer_price="";
        String distributor_price="";
        String professional_price="";
        String carpenter_price="";
        String pack_of="";
        String status="";
        String created="";


        public String getId() {
            return id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getOption_id() {
            return option_id;
        }

        public String getOption_value_id() {
            return option_value_id;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getMrp() {
            return mrp;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public String getDealer_price() {
            return dealer_price;
        }

        public String getDistributor_price() {
            return distributor_price;
        }

        public String getProfessional_price() {
            return professional_price;
        }

        public String getCarpenter_price() {
            return carpenter_price;
        }

        public String getPack_of() {
            return pack_of;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public List<Productoptionclass> getScheme() {
            return Scheme;
        }

        public Option_class getOption() {
            return Option;
        }

        public Option_value_class getOptionValue() {
            return OptionValue;
        }

    }

    public class Option_class
    {
        String id="";
        String name="";
        String sort_order="";
        String status="";
        String created="";
        String modified="";

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getSort_order() {
            return sort_order;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }
    }
    public class Option_value_class
    {
        String id="";
        String name="";
        String option_id="";
        String sort_order="";
        String status="";
        String created="";
        String modified="";

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOption_id() {
            return option_id;
        }

        public String getSort_order() {
            return sort_order;
        }

        public String getStatus() {
            return status;
        }

        public String getCreated() {
            return created;
        }

        public String getModified() {
            return modified;
        }
    }


}
