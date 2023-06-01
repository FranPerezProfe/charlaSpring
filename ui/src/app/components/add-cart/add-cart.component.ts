import { Component, OnInit } from '@angular/core';
import { Cart } from 'src/app/models/cart.model';
import { Product } from 'src/app/models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-cart',
  templateUrl: './add-cart.component.html',
  styleUrls: ['./add-cart.component.css']
})
export class AddCartComponent implements OnInit {

  cart: Cart = {
    id: '',
    items: []
  };
  submitted = false;

  products: Product[] = [];
  selectedProducts: Product[] = [];

  constructor(private cartService: CartService, private productService: ProductService) { }

  ngOnInit(): void {
      this.productService.getAll()
            .subscribe({
              next: (data) => {
                this.products = data;
                console.log(data);
              },
              error: (e) => console.error(e)
            });
    }

  saveCart(): void {
    const data = {
      id: this.cart.id,
      items: this.selectedProducts.map(product => product.id)
    };

    this.cartService.create(data)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.submitted = true;
        },
        error: (e) => console.error(e)
      });
  }

  newCart(): void {
    this.submitted = false;
    this.cart = {
      id: '',
      items: []
    };
  }

}
