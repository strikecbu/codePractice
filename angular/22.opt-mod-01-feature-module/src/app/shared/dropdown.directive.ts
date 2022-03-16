import {
  Directive,
  ElementRef,
  HostBinding,
  HostListener,
} from "@angular/core";

@Directive({
  selector: "[appDropdown]",
})
export class DropdownDirective {
  constructor(private eleRef: ElementRef) {}

  @HostBinding("class.open") isOpen = false;

  @HostListener("document:click", ["$event"]) toggleOpen(event: MouseEvent) {
    if (this.eleRef.nativeElement.contains(event.target)) {
      this.isOpen = !this.isOpen;
    } else {
      this.isOpen = false;
    }
  }
}
