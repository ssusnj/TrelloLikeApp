import { Routes } from '@angular/router';
import { BoardsComponent } from './component/boards/boards.component';
import { BoardComponent } from './component/board/board.component';
import { LoginComponent } from './component/login/login.component';
import { authGuard } from './auth.guard';

export const routes: Routes = [
  { path: 'api/login', component: LoginComponent },
  { path: 'api/boards/:username', component: BoardsComponent, canActivate: [authGuard] },
  { path: 'api/boards/:username/:id', component: BoardComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'api/login', pathMatch: 'full' },
  { path: '**', redirectTo: 'api/login' }
];
