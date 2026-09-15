import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { IonMenu, IonHeader, IonToolbar, IonTitle, IonContent, IonAccordion, IonAccordionGroup, IonList, IonItem, IonIcon, IonLabel } from '@ionic/angular';
import { MenuService } from '../../core/config/menu.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    IonMenu,
    IonHeader,
    IonToolbar,
    IonTitle,
    IonContent,
    IonAccordion,
    IonAccordionGroup,
    IonList,
    IonItem,
    IonIcon,
    IonLabel
  ]
})
export class MenuComponent implements OnInit {
  constructor(public menuService: MenuService) {}

  ngOnInit(): void {}
}


