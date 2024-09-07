import { Component, OnInit } from '@angular/core';
import { BoardResponse, BoardService } from '../../services/board.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-boards',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './boards.component.html',
  styleUrl: './boards.component.css'
})
export class BoardsComponent implements OnInit {

  boardResponses: BoardResponse[];

  constructor(private boardService: BoardService) {}

  ngOnInit(): void {
    this.boardService.getBoards().subscribe(
     (results: BoardResponse[]) => {
      console.log(results)
      this.boardResponses = results;
     })
  } 

}
