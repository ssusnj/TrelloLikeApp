import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { LoginComponent } from './component/login/login.component';
import { HeaderComponent } from "./component/header/header.component";
import { BoardsComponent } from './component/boards/boards.component';
import { CommonModule } from '@angular/common';
import { BoardComponent } from "./component/board/board.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, BoardComponent, FormsModule, LoginComponent, HeaderComponent, BoardsComponent, CommonModule, BoardComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent { }
