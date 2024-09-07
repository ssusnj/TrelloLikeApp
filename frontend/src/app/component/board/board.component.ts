import { Component, OnInit } from '@angular/core';
import { BoardResponse, BoardService } from '../../services/board.service';
import { ActivatedRoute, ParamMap, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.css'
})
export class BoardComponent implements OnInit {

  board: BoardResponse;

  constructor(private activatedRoute: ActivatedRoute, private boardService: BoardService) {}

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      this.boardService.getBoard(+params.get("id")!).subscribe( // can be null, that's why ! , + is for number
        (response: BoardResponse) => {
         console.log(response);
         this.board = response;
        })
    })
  }

}
