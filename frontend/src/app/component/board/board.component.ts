import { Component, HostListener, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthenticationService } from '../../services/authentication.service';
import { BoardService } from '../../services/board.service';
import { ListService } from '../../services/list.service';
import { Board } from '../../interfaces/board.interface';
import { Card } from '../../interfaces/card.interface';
import { List } from '../../interfaces/list.interface';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './board.component.html',
  styleUrl: './board.component.css'
})
export class BoardComponent implements OnInit {

  username: string = '';
  board: Board = { id: 0, name: '', description: '', lists: [] };
  newList: List = { id: 0, name: '', cards: [] };
  newCard: Card = { id: 0, title: '', description: '' };
  selectedCard: Card = { id: 0, title: '', description: '' };

  dropdownOpenId: number | null = null;
  showCardFormId: number | null = null;
  showListForm: boolean = false;
  showEditCardForm: boolean = false;

  constructor(private activatedRoute: ActivatedRoute, 
    private router: Router,  
    private authService: AuthenticationService,
    private listService: ListService,
    private boardService: BoardService) {}

  ngOnInit() { 
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      this.username = this.authService.getUsername();
      this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
        this.boardService.getBoard(this.username, +params.get("id")!, access_token).subscribe(data => {
           this.board = data
        },
        error => {
          console.error('Error deleting board:', error);
          this.router.navigate(['api/boards', this.username]);
        }
      )})
    }
  }

  onDeleteBoard() {
    if (confirm('Are you sure you want to delete this board?')) {
      const access_token = this.authService.getAuthToken();
      if (access_token != null) {  
        this.boardService.deleteBoard(this.username, this.board.id, access_token).subscribe(data => {
          this.board = { id: 0, name: '', description: '', lists: [] };
          this.router.navigate(['api/boards', this.username]);
        },
        error => {
          console.error('Error deleting board:', error);
        }
      )}
    }
  }

  onAddList() {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      if (this.newList.name.trim() !== '') {
        this.listService.addList(this.username, this.board.id, this.newList, access_token).subscribe(data => {
          this.board.lists.push({ ...this.newList, id: data.id, cards: []});
          this.newList = { id: 0, name: '', cards: [] };
          this.showListForm = false;
        });
      }
    }
  }

  onDeleteList(listId: number) {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      this.listService.deleteList(this.username, this.board.id, listId, access_token).subscribe(data => 
        this.board.lists = this.board.lists.filter(list => list.id !== listId)
      )
    }
  }

  onAddCardToList(listId: number) {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      const list = this.board.lists.find(l => l.id === listId);
      if (list) {
        this.listService.addCard(this.username, this.board.id, listId, this.newCard.title, access_token).subscribe(data => {
          list.cards.push({ ...data });
          this.newCard = { id: 0, title: '', description: '' };
          this.showCardFormId = null;
        })
      }
    }
  }

  onEditCard() {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      const list = this.board.lists.find(l => l.cards.some(card => card.id === this.selectedCard.id));
      if (list) {
        this.listService.updateCard(this.username, this.board.id, list.id, this.selectedCard, access_token).subscribe(data => {
          const cardIndex = list.cards.findIndex(card => card.id === this.selectedCard.id);
          if (cardIndex !== -1) {
            list.cards[cardIndex] = { ...data };
          }
          this.newCard = { id: 0, title: '', description: '' };
          this.showCardFormId = null;
          this.showEditCardForm = false;
        })
      }
    }
  }
  
  onDeleteCard() {
    const access_token = this.authService.getAuthToken();
    if (access_token != null) {
      const list = this.board.lists.find(l => l.cards.some(card => card.id === this.selectedCard.id))
      if (list) {
        this.listService.deleteCard(this.username, this.board.id, list.id, this.selectedCard,  access_token).subscribe(data => {
          list.cards = list.cards.filter(card => card.id !== this.selectedCard.id)
          this.newCard = { id: 0, title: '', description: '' };
          this.showCardFormId = null;
          this.showEditCardForm = false;
        })
      }
    }
  }
 
  onToggleDropdown(listId: number) {
    this.dropdownOpenId = this.dropdownOpenId === listId ? null : listId;
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.dropdown')) {
      this.dropdownOpenId = null;
    }
  }

  onShowCardForm(listId: number) {
    this.showCardFormId = listId;
  }
  
  onOpenEditCard(card: Card) {
    this.selectedCard = { ...card };
    this.showEditCardForm = true;
  }
}
