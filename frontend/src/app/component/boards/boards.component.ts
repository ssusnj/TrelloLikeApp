import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { BoardService } from '../../services/board.service';
import { Board } from '../../interfaces/board.interface';
import { BoardComponent } from '../board/board.component';

@Component({
  selector: 'app-boards',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, BoardComponent],
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.css']
})
export class BoardsComponent implements OnInit {

  boards: Board[] = [];
  showForm: boolean = false;
  username: string = '';
  newBoard = { name: '', description: ''};
  
  constructor(private boardService: BoardService, 
    private authService: AuthenticationService, 
    private router: Router) {}

  ngOnInit(): void {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      this.username = this.authService.getUsername();
      this.boardService.getBoards(this.username, access_token).subscribe(data=> {
          this.boards = data;
        },
        error => {
          console.error('Error loading boards:', error);
        }
      );
    } else {
      console.log('NOT AUTHORIZED');
    }
  } 

  onCreateBoard() {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      if (this.newBoard.name.trim() !== '') {
        this.boardService.addBoard(this.username, this.newBoard, access_token).subscribe(data => {
          this.boards.push(data);
          this.newBoard = { name: '', description: '' };
          this.showForm = false;
        }, error => {
          console.error('Error creating board:', error);
        });
      }
    }
  }

  onNavigateToBoard(boardId: number) {
    this.router.navigate(['api/boards', this.username, boardId]); 
  }
}
