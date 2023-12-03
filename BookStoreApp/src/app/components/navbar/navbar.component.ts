import { Component,OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  private users: any[] = [];
  private storedUsers = localStorage.getItem("user");
  constructor(private router: Router) {
    
    this.users = this.storedUsers ? JSON.parse(this.storedUsers) : [];
    this.showProfile()
  }

  ngOnInit(): void {
    this.showProfile();
  }
  
    storedUser:any = localStorage.getItem('user');
   user:User= this.storedUser ? JSON.parse(this.storedUser) : null;
   name:string|null=null;
   email:string| null = null;
   nameSF:string|null=null;
   

   showProfile()
   {
    if(this.user)
    {
      
      this.email = this.user.username;
      
        if(this.user)
      {
        
        this.name = this.user.name;
       const nameArray = this.name.split(" ");
        
       const capitalizedArray = nameArray.map(word => word.charAt(0).toUpperCase());
      
       this.nameSF = capitalizedArray.join(" ");
      }
      
    }
   }

   signOut()
   {
     localStorage.removeItem("user");
     localStorage.removeItem("token");
     this.storedUsers = null;
     this.name= null;
     this.email= null;
     this.nameSF = null;

     if(this.router.url ==='/readerlist')
     {
      Swal.fire({
        title: 'Error!',
        text: "User have to login in order to visit reading list",
        icon: 'error',
        confirmButtonText: 'Cool'
      })
      
      this.router.navigate(['login']);
     }
   }

   naviagteToReadingList()
   {
    if(this.storedUsers)
    {
     this.router.navigate(['/readinglist']); 
    }
    else
    {
      Swal.fire({
        title: 'Error!',
        text: "User have to login in order to visit raeding list",
        icon: 'error',
        confirmButtonText: 'Cool'
      })
     this.router.navigate(["./login"]);
    }

   }
}


export interface User {
  id: number;
  name: string;
  username: string;
  role: string;
  discussionContents: any[]; // adjust the type accordingly
  bookReviews: any[]; // adjust the type accordingly
  readingLists: any[]; // adjust the type accordingly
}