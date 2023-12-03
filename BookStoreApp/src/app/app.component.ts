import { Component, OnInit } from '@angular/core';
import { initFlowbite } from 'flowbite';
import {Router, NavigationEnd, ActivatedRoute} from '@angular/router'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {


   ngOnInit(): void {
     initFlowbite();
   }

  title = 'BookStoreApp';
  showNavbar: boolean = true;

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {
    // Subscribe to route changes
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Set showNavbar based on the current route
        this.showNavbar = !this.isLoginOrSignupRoute(this.activatedRoute);
      }
    });
  }

  // Helper function to determine if the current route is a login or signup route
  isLoginOrSignupRoute(route: ActivatedRoute): boolean {
    if (route.firstChild) {
      const childRoute = route.firstChild;
      const path = childRoute.snapshot.routeConfig?.path;
      return path === 'login' || path === 'signup' || path === 'registration';
    }
    return false;
  }
}
