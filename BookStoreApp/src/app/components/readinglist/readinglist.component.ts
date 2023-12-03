import { Component,OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReadingListService } from 'src/app/services/reading-list.service';
import { DatePipe } from '@angular/common';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-readinglist',
  templateUrl: './readinglist.component.html',
  styleUrls: ['./readinglist.component.css']
})
export class ReadinglistComponent implements OnInit{

  readingListItems:any;

  constructor( private router:Router,private service:ReadingListService, private datePipe:DatePipe){

    this.getReadingList();
  };

  ngOnInit(): void {
   
  }
  getReadingList()
  {
   this.service.getReadingList().subscribe(
    (response) => {
      this.readingListItems = response;
     
    },
    (error) => {
      // Handle the error here
      console.error('Error fetching reading list', error);
    });
   
   
  }

  removeFromReadingList(itemId:number)
  {
    this.service.removeFromReadingList(itemId).subscribe(
      (response) => {
       
        this.getReadingList();
        Swal.fire({
          title: 'Success',
          text: "Book has been removed",
          icon: 'success',
          timer: 1500
        })
      },
      (error) => {
        console.error("Error fetching discussions", error);
      }
    );;
   
  }
  
  back() {
    
    this.router.navigate(['']); // Adjust the route as per your app's configuration
  }


  formatDate(dateTime: number[]) {
    const [year, month, day, hours, minutes, seconds, milliseconds] = dateTime;
    const dateObject = new Date(year, month - 1, day, hours, minutes, seconds, milliseconds);
    return this.datePipe.transform(dateObject, 'medium');
  }
}
