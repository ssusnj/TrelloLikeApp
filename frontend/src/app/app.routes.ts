import { Routes } from '@angular/router';
import { BoardsComponent } from './component/boards/boards.component';
import { BoardComponent } from './component/board/board.component';

export const routes: Routes = [
    { path:'api/boards', component: BoardsComponent },
    { path:'api/boards/:id', component: BoardComponent },
    { path:'**', redirectTo: 'api/boards' }
];
