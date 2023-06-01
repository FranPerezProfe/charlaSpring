import { Component, Input, OnInit } from '@angular/core';
import { CartService } from 'src/app/services/cart.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Cart } from 'src/app/models/cart.model';
import { ProductService } from 'src/app/services/product.service';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-cart-details',
  templateUrl: './cart-details.component.html',
  styleUrls: ['./cart-details.component.css']
})
export class CartDetailsComponent implements OnInit {

  @Input() viewMode = false;

  @Input() currentCart: Cart = {
    id: '',
    items: []
  };

  message = '';

  products: Product[] = [];
  selectedProducts: Product[] = [];

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getCart(this.route.snapshot.params["id"]);
    }
    this.productService.getAll()
                .subscribe({
                  next: (data) => {
                    this.products = data;
                    console.log(data);
                  },
                  error: (e) => console.error(e)
                });
  }

  getCart(id: string): void {
    this.cartService.get(id)
      .subscribe({
        next: (data) => {
          this.currentCart = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  updateCart(): void {
    console.log(this.selectedProducts);
    this.message = '';
    var itemsToAdd = this.currentCart.items?.map(item =>  item.id);
    itemsToAdd?.push(...this.selectedProducts.map(a => a.id));
    itemsToAdd = itemsToAdd?.filter(
          (thing, i, arr) => arr.findIndex(t => t === thing) === i
        );
    itemsToAdd = itemsToAdd?.filter((value) => value != null);
    var cartToUpdate: Cart = {
        id: this.currentCart.id,
        items: itemsToAdd
      };
    this.cartService.update(this.currentCart.id, cartToUpdate)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message ? res.message : 'This cart was updated successfully!';
          this.currentCart = res;
        },
        error: (e) => console.error(e)
      });
  }

  deleteCart(): void {
    this.cartService.delete(this.currentCart.id)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.router.navigate(['/carts']);
        },
        error: (e) => console.error(e)
      });
  }

  deleteItem(index: number) {
    this.currentCart.items?.splice(index, 1);
  }

  productNotInCart(product: Product) : boolean {
    return this.currentCart?.items?.map(item => item.id).indexOf(product?.id) === -1
  }

}
