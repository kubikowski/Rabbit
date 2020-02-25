import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReceiveLogsComponent } from './receive-logs.component';

describe('ReceiveLogsComponent', () => {
  let component: ReceiveLogsComponent;
  let fixture: ComponentFixture<ReceiveLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReceiveLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReceiveLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
