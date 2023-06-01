import { Component, OnInit } from '@angular/core';
import { Cart } from 'src/app/models/cart.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-carts-list',
  templateUrl: './carts-list.component.html',
  styleUrls: ['./carts-list.component.css']
})
export class CartsListComponent implements OnInit {

  carts?: Cart[];
  currentCart: Cart = {};
  currentIndex = -1;
  title = '';

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.retrieveCarts();
  }

  retrieveCarts(): void {
    this.cartService.getAll()
      .subscribe({
        next: (data) => {
          this.carts = data;
          console.log(data);
        },
        error: (e) => console.error(e)
      });
  }

  refreshList(): void {
    this.retrieveCarts();
    this.currentCart = {};
    this.currentIndex = -1;
  }

  setActiveCart(cart: Cart, index: number): void {
    this.currentCart = cart;
    this.currentIndex = index;
  }

  removeAllCarts(): void {
    this.cartService.deleteAll()
      .subscribe({
        next: (res) => {
          console.log(res);
          this.refreshList();
        },
        error: (e) => console.error(e)
      });
  }

}
