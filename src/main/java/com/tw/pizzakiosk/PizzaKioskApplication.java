package com.tw.pizzakiosk;

import com.tw.pizzakiosk.crusts.Crust;
import com.tw.pizzakiosk.sizes.Size;
import com.tw.pizzakiosk.toppings.Topping;
import com.tw.pizzakiosk.utils.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@EnableAutoConfiguration
public class PizzaKioskApplication {


        @RequestMapping("/")
        public String home(Model model) {
            model.addAttribute("sizes", DataSource.instance().getSizeMap().keySet());
            model.addAttribute("crustTypes", DataSource.instance().getCrustsList());
            model.addAttribute("toppings", DataSource.instance().getToppingMap().keySet());
            return "OrderPizza";
        }

        @RequestMapping(value = "/orderPizza", method = RequestMethod.POST)
        public String orderPizza(@ModelAttribute(value="orderPizzaForm") OrderPizzaForm orderPizzaForm, Model model) throws Exception {
            Map<String, Size> sizeMap = DataSource.instance().getSizeMap();
            List<Crust> crustsList = DataSource.instance().getCrustsList();
            Map<String, Topping> toppingMap = DataSource.instance().getToppingMap();
            model.addAttribute("sizes", sizeMap.keySet());
            model.addAttribute("crustTypes", crustsList);
            model.addAttribute("toppings", toppingMap.keySet());
            try {
                PizzaKiosk kiosk = PizzaKiosk.getInstance();
                double price = kiosk.makePizzaWith(crustsList.get(0),orderPizzaForm.getSize(),orderPizzaForm.getToppings());
                model.addAttribute("price", price);
            }catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("price", 0);
            }
            return "OrderPizza";
        }

        public static void main(String[] args) throws Exception {
            SpringApplication.run(PizzaKioskApplication.class, args);
        }
    }
