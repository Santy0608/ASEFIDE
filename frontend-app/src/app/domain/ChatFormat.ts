import { Pipe, PipeTransform } from "@angular/core";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";


@Pipe({ name: 'chatFormat' })
export class ChatFormatPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {}

  transform(value: string): SafeHtml {
    if (!value) return '';

    let html = value
      // Negritas **texto**
      .replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>')
      // Viñetas con •
      .replace(/^• (.+)$/gm, '<li>$1</li>')
      // Saltos de línea
      .replace(/\n\n/g, '</p><p>')
      .replace(/\n/g, '<br>');

    // Envolver listas
    html = html.replace(/(<li>.*?<\/li>)+/gs, '<ul>$&</ul>');
    html = `<p>${html}</p>`;

    return this.sanitizer.bypassSecurityTrustHtml(html);
  }
}