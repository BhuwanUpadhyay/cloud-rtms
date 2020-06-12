### FormArray in Angular Material

```
      <ng-container formArrayName="productLines">
        <ng-template matStepLabel>Product Lines</ng-template>
        <ng-container *ngFor="let _ of formProductLines.controls; index as i">
          <ng-container [formGroupName]="i">
            <mat-form-field>
              <mat-label>Product</mat-label>
              <input formControlName="productId" matInput required />
            </mat-form-field>
            <mat-form-field>
              <mat-label>Quantity</mat-label>
              <input formControlName="quantity" matInput required />
            </mat-form-field>
          </ng-container>
        </ng-container>
        <div>
          <button color="warn" mat-flat-button matStepperPrevious>Back</button>
          <button (click)="addProductLine()" color="primary" mat-flat-button>Add Row</button>
          <button color="accent" mat-flat-button matStepperNext>Next</button>
        </div>
      </ng-container>
```
