import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router, RouterLink, RouterModule } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardTransaccion } from '../../domain/DashboardTransaccion';
import { DashboardTipoAhorro } from '../../domain/DashboardTipoAhorro';
 
import { Chart, registerables } from 'chart.js';
import { BaseChartDirective } from 'ng2-charts';
import { DashboardPrestamoEstado } from '../../domain/DashboardPrestamosEstado';
import { DashboardActividadesProximas } from '../../domain/DashboardActividadesProximas';
 
Chart.register(...registerables);
 
@Component({
  selector: 'app-dashboard',
  imports: [RouterModule, RouterLink, CommonModule, BaseChartDirective],
  styles: [`
 
    /* ── Variables globales ── */
    :host {
      --blue:   #3266ad;
      --teal:   #1d9e75;
      --amber:  #ba7517;
      --coral:  #d85a30;
      --purple: #534ab7;
      --text-muted: #6b7280;
      --border: rgba(0,0,0,0.08);
      --radius-md: 8px;
      --radius-lg: 12px;
    }
 
    /* ── Layout ── */
    .dash-wrapper {
      padding: 1.5rem;
      max-width: 1280px;
      margin: 0 auto;
    }
 
    .dash-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 1.5rem;
      padding-bottom: 1rem;
      border-bottom: 0.5px solid var(--border);
    }
 
    .dash-header h2 {
      font-size: 18px;
      font-weight: 500;
      margin: 0;
    }
 
    .dash-header span {
      font-size: 13px;
      color: var(--text-muted);
    }
 
    /* ── KPI Cards ── */
    .kpi-grid {
      display: grid;
      grid-template-columns: repeat(4, minmax(0, 1fr));
      gap: 12px;
      margin-bottom: 1.5rem;
    }
 
    @media (max-width: 992px) {
      .kpi-grid { grid-template-columns: repeat(2, 1fr); }
    }
 
    @media (max-width: 576px) {
      .kpi-grid { grid-template-columns: 1fr; }
    }
 
    .kpi-card {
      background: #f9fafb;
      border-radius: var(--radius-md);
      padding: 1rem;
      border: none;
    }
 
    .kpi-label {
      font-size: 12px;
      color: var(--text-muted);
      margin-bottom: 6px;
    }
 
    .kpi-value {
      font-size: 22px;
      font-weight: 500;
      color: #111827;
      margin: 0;
    }
 
    .kpi-badge {
      display: inline-block;
      font-size: 11px;
      padding: 2px 8px;
      border-radius: var(--radius-md);
      margin-top: 6px;
    }
 
    .kpi-badge.up   { background: #d1fae5; color: #065f46; }
    .kpi-badge.down { background: #fee2e2; color: #991b1b; }
    .kpi-badge.info { background: #dbeafe; color: #1e40af; }
 
    /* ── Charts grid ── */
    .charts-row {
      display: grid;
      grid-template-columns: minmax(0, 1.6fr) minmax(0, 1fr);
      gap: 12px;
      margin-bottom: 12px;
    }
 
    @media (max-width: 900px) {
      .charts-row { grid-template-columns: 1fr; }
    }
 
    /* ── Card base ── */
    .chart-card {
      background: #ffffff;
      border: 0.5px solid var(--border);
      border-radius: var(--radius-lg);
      padding: 1rem 1.25rem;
    }
 
    .chart-card-title {
      font-size: 13px;
      font-weight: 500;
      color: var(--text-muted);
      margin-bottom: 0.75rem;
      text-transform: uppercase;
      letter-spacing: 0.04em;
    }
 
    /* ── Bar chart wrapper ── */
    .bar-wrapper {
      position: relative;
      height: 240px;
    }
 
    /* ── Dona chart ── */
    .dona-layout {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 1rem;
      align-items: center;
    }
 
    @media (max-width: 600px) {
      .dona-layout { grid-template-columns: 1fr; }
    }
 
    .canvas-wrapper {
      position: relative;
      height: 220px;
    }
 
    .dona-center {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      text-align: center;
      pointer-events: none;
    }
 
    .dona-center .center-label {
      font-size: 11px;
      color: var(--text-muted);
      display: block;
    }
 
    .dona-center .center-value {
      font-size: 17px;
      font-weight: 500;
      color: #111827;
    }
 
    /* ── Leyenda interactiva ── */
    .legend-list {
      display: flex;
      flex-direction: column;
      gap: 6px;
    }
 
    .leg-item {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 6px 8px;
      border-radius: var(--radius-md);
      cursor: pointer;
      transition: opacity 0.2s, background 0.15s;
      user-select: none;
    }
 
    .leg-item:hover {
      background: #f3f4f6;
    }
 
    .leg-item.leg-hidden {
      opacity: 0.35;
    }
 
    .leg-dot {
      width: 10px;
      height: 10px;
      border-radius: 2px;
      flex-shrink: 0;
    }
 
    .leg-name {
      font-size: 13px;
      font-weight: 500;
      color: #111827;
      flex: 1;
    }
 
    .leg-pct {
      font-size: 12px;
      color: var(--text-muted);
    }
 
    .leg-count {
      font-size: 11px;
      background: #f3f4f6;
      color: var(--text-muted);
      padding: 2px 7px;
      border-radius: 99px;
    }
 
    /* ── Totales ── */
    .totales-row {
      display: flex;
      justify-content: center;
      gap: 2rem;
      padding-top: 0.75rem;
      margin-top: 0.75rem;
      border-top: 0.5px solid var(--border);
      font-size: 13px;
      color: var(--text-muted);
    }
 
    /* ── Custom Chart.js legend (bar) ── */
    .bar-legend {
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
      margin-bottom: 8px;
      font-size: 12px;
      color: var(--text-muted);
    }
 
    .bar-legend span {
      display: flex;
      align-items: center;
      gap: 4px;
    }
 
    .bar-legend .dot {
      width: 10px;
      height: 10px;
      border-radius: 2px;
      display: inline-block;
    }
 
  `],
  template: `
 
    <div class="dash-wrapper">
 
      <!-- Encabezado -->
      <div class="dash-header">
        <h2>Panel de administración — Asociación Solidarista</h2>
        <span>Datos actualizados al mes en curso</span>
      </div>
 
      <!-- KPIs -->
      <div class="kpi-grid">
 
        <div class="kpi-card">
          <div class="kpi-label">Asociados activos</div>
          <div class="kpi-value">{{ kpis?.totalAsociadosActivos ?? '—' }}</div>
          <span class="kpi-badge up">Activos en sistema</span>
        </div>
 
        <div class="kpi-card">
          <div class="kpi-label">Saldo total en ahorros</div>
          <div class="kpi-value">{{ kpis?.totalAhorros | currency:'CRC':'symbol-narrow':'1.0-0' }}</div>
          <span class="kpi-badge up">Todas las cuentas</span>
        </div>
 
        <div class="kpi-card">
          <div class="kpi-label">Préstamos pendientes</div>
          <div class="kpi-value">{{ kpis?.totalPrestamosPendientes ?? '—' }}</div>
          <span class="kpi-badge down">Sin finalizar</span>
        </div>
 
        <div class="kpi-card">
          <div class="kpi-label">Transacciones del mes</div>
          <div class="kpi-value">{{ kpis?.transaccionesMesActual ?? '—' }}</div>
          <span class="kpi-badge info">Mes actual</span>
        </div>
 
      </div>
 
      <!-- Fila principal de gráficos -->
      <div class="charts-row">
 
        <!-- Gráfico de barras apiladas -->
        <div class="chart-card">
          <div class="chart-card-title">Transacciones por tipo — últimos 6 meses</div>
 
          <!-- Leyenda HTML personalizada -->
          <div class="bar-legend">
            <span *ngFor="let ds of barChartData?.datasets">
              <span class="dot" [style.background]="ds.backgroundColor"></span>
              {{ ds.label }}
            </span>
          </div>
 
          <div class="bar-wrapper" *ngIf="barChartData">
            <canvas baseChart
                    [data]="barChartData"
                    [options]="barChartOptions"
                    [type]="'bar'">
            </canvas>
          </div>
        </div>
 
        <!-- Gráfico de dona -->
        <div class="chart-card">
          <div class="chart-card-title">Distribución de ahorros por tipo</div>
 
          <div class="dona-layout">
 
            <!-- Canvas con valor central superpuesto -->
            <div class="canvas-wrapper">
              <canvas #donaCanvas></canvas>
              <div class="dona-center">
                <span class="center-label">Saldo total</span>
                <span class="center-value">₡{{ saldoGlobal | number:'1.0-0' }}</span>
              </div>
            </div>
 
            <!-- Leyenda interactiva lateral -->
            <div class="legend-list">
              <div
                *ngFor="let item of datos; let i = index"
                class="leg-item"
                [class.leg-hidden]="isCategoryHidden(i)"
                (click)="toggleCategory(i)">
 
                <span class="leg-dot" [style.background]="colores[i]"></span>
                <span class="leg-name">{{ item.tipoAhorro }}</span>
                <span class="leg-pct">{{ item.porcentajeCuentas }}%</span>
                <span class="leg-count">{{ item.totalCuentas }}</span>
              </div>
            </div>
 
          </div>
 
          <div class="totales-row">
            <span>{{ totalCuentas }} cuentas activas</span>
          </div>
        </div>

        <!-- Segundo Gráfico -->
        <div class="chart-card">
          <div class="chart-card-title">Estado de préstamos — saldo pendiente</div>
          <div style="position: relative; height: 200px;">
            <canvas #prestamosCanvas></canvas>
          </div>
        </div>

        <div class="chart-card">
          <div class="chart-card-title">Inscripciones — próximos eventos</div>

          <!-- Leyenda HTML -->
          <div class="bar-legend">
            <span><span class="dot" style="background: rgba(50,102,173,0.35)"></span>Cupo total</span>
            <span><span class="dot" style="background: #3266ad"></span>Inscritos</span>
          </div>

          <div style="position: relative; height: 200px;">
            <canvas #actividadesCanvas></canvas>
          </div>
        </div>
 
      </div>
    </div>
 
  `
})
export class DashboardComponent implements OnInit, OnDestroy {
 
  @ViewChild('donaCanvas') canvasRef!: ElementRef<HTMLCanvasElement>;
  @ViewChild('prestamosCanvas') prestamosCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('actividadesCanvas') actividadesCanvas!: ElementRef<HTMLCanvasElement>;

  datosActividades: DashboardActividadesProximas[] = [];
  private actividadesChart?: Chart;

  kpis: any;
  dashboardTransaccion: DashboardTransaccion[] = [];
  datos: DashboardTipoAhorro[] = [];
  totalCuentas = 0;
  saldoGlobal   = 0;
  hiddenCategories: Set<number> = new Set();

  datosPrestamos: DashboardPrestamoEstado[] = [];
 
  /* ── Chart.js ── */
  barChartData: any;
  barChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: { legend: { display: false } },   
    scales: {
      x: {
        stacked: true,
        grid: { display: false },
        ticks: { autoSkip: false, font: { size: 11 } }
      },
      y: {
        stacked: true,
        beginAtZero: true,
        ticks: { font: { size: 11 } },
        grid: { color: 'rgba(0,0,0,0.06)' }
      }
    }
  };
 
  private donaChart?: Chart;
 
  colores = ['#3266ad', '#1d9e75', '#ba7517', '#d85a30', '#534ab7'];
 
  constructor(
    private dashboardService: DashboardService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
 
  ngOnInit(): void {
    this.cargarKPIs();
    this.cargarChart();
    this.cargarChartTiposAhorros();
    this.cargarPrestamosPorEstado();
    this.cargarActividadesProximas();
  }
 
  ngOnDestroy(): void {
    this.donaChart?.destroy();
    this.actividadesChart?.destroy();
  }
 
  /* ── KPIs ── */
  cargarKPIs(): void {
    this.dashboardService.getKPIs().subscribe(data => {
      this.kpis = data;
    });
  }
 
  cargarChart(): void {
    this.dashboardService.obtenerDashboardTransacciones().subscribe(res => {
      this.dashboardTransaccion = res;
      this.renderBarChart();
    });
  }
 
  renderBarChart(): void {
    const labels  = [...new Set(this.dashboardTransaccion.map(d => d.mes))];
    const tipos   = [...new Set(this.dashboardTransaccion.map(d => d.tipo))];
 
    const datasets = tipos.map(tipo => ({
      label: tipo,
      data: labels.map(mes => {
        const reg = this.dashboardTransaccion.find(d => d.mes === mes && d.tipo === tipo);
        return reg ? reg.cantidad : 0;
      }),
      backgroundColor: this.getColor(tipo),
      borderColor: '#ffffff',
      borderWidth: 1
    }));
 
    this.barChartData = { labels, datasets };
  }
 
  getColor(tipo: string): string {
    const map: Record<string, string> = {
      'Depósito':                    '#2ecc71',
      'Retiro':                      '#e74c3c',
      'Pago de Préstamo':            '#3498db',
      'Transferencia':               '#9b59b6',
      'Interés':                     '#f1c40f',
      'Desembolso':                  '#e67e22',
      'Comisión':                    '#34495e',
      'Subsidio':                    '#1abc9c',
      'Penalización':                '#c0392b',
      'Excedentes':                  '#27ae60',
      'Bono':                        '#f39c12',
      'Capitalización':              '#8e44ad',
      'Donación':                    '#ff9ff3',
      'Devolución':                  '#54a0ff',
      'Ajuste':                      '#8395a7',
      'Tipo de Transacción Ahorros': '#48dbfb'
    };
    return map[tipo.trim()] ?? '#999999';
  }
 
  /* ── Dona ── */
  cargarChartTiposAhorros(): void {
    this.dashboardService.obtenerDashboardTiposAhorros().subscribe(data => {
      this.datos       = data;
      this.totalCuentas = data.reduce((s, d) => s + d.totalCuentas, 0);
      this.saldoGlobal  = data.reduce((s, d) => s + d.saldoTotal,   0);
      this.buildDonaChart(data);
    });
  }
 
  private buildDonaChart(data: DashboardTipoAhorro[]): void {
    const datosFiltrados = data.filter(d => d.saldoTotal > 0);
 
    this.donaChart?.destroy();
 
    this.donaChart = new Chart(this.canvasRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: datosFiltrados.map(d => d.tipoAhorro),
        datasets: [{
          data:            datosFiltrados.map(d => d.saldoTotal),
          backgroundColor: this.colores.slice(0, datosFiltrados.length),
          hoverOffset:     15,
          borderWidth:     2,
          borderColor:     '#ffffff'
        }]
      },
      options: {
        responsive:          true,
        maintainAspectRatio: false,
        cutout:              '75%',
        plugins: {
          legend: { display: false },
          tooltip: {
            backgroundColor: 'rgba(0,0,0,0.8)',
            padding: 12,
            callbacks: {
              label: ctx => ` Saldo: ₡${ctx.parsed.toLocaleString('es-CR')}`
            }
          }
        }
      },
      plugins: [{
        id: 'chartClick',
        beforeInit: chart => {
          chart.canvas.addEventListener('click', e => {
            const pts = chart.getElementsAtEventForMode(e, 'nearest', { intersect: true }, true);
            if (pts.length > 0) {
              const label = chart.data.labels![pts[0].index] as string;
              const idx   = this.datos.findIndex(d => d.tipoAhorro === label);
              if (idx !== -1) this.toggleCategory(idx);
            }
          });
        }
      }]
    });
  }
 
  /* ── Interactividad leyenda ── */
  toggleCategory(index: number): void {
    this.hiddenCategories.has(index)
      ? this.hiddenCategories.delete(index)
      : this.hiddenCategories.add(index);
 
    this.updateDonaVisibility();
  }
 
  isCategoryHidden(index: number): boolean {
    return this.hiddenCategories.has(index);
  }
 
  private updateDonaVisibility(): void {
    if (!this.donaChart?.data?.labels) return;
 
    const visible = this.datos.filter((_, i) => !this.hiddenCategories.has(i));
    const src     = visible.length > 0 ? visible : this.datos;
 
    this.donaChart.data.labels               = src.map(d => d.tipoAhorro);
    this.donaChart.data.datasets![0].data    = src.map(d => d.saldoTotal);
    this.donaChart.data.datasets![0].backgroundColor =
      src.map((_, i) => this.colores[this.datos.indexOf(src[i])]);
 
    this.donaChart.update('active');
  }


  getColorEstado(estado: string): string {
    const map: Record<string, string> = {
      'Completado':   '#1d9e75',
      'En proceso': '#3266ad',
      'INACTIVO':  '#d85a30',
      'Finalizado': '#888780',
      'Pendiente':  '#ba7517'
    };
    return map[estado.trim()] ?? '#999999';
  }

  cargarPrestamosPorEstado(): void {
    this.dashboardService.obtenerDashboardPrestamosEstado().subscribe(data => {
      this.datosPrestamos = data;
      this.buildBarHorizontalChart(data);
    });
  }

  private buildBarHorizontalChart(data: DashboardPrestamoEstado[]): void {
    new Chart(this.prestamosCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: data.map(d => d.estado),
        datasets: [{
          data:            data.map(d => d.saldoPendienteTotal),
          backgroundColor: data.map(d => this.getColorEstado(d.estado)),
          borderRadius:    4,
          borderWidth:     0
        }]
      },
      options: {
        indexAxis: 'y',          
        responsive:          true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          x: {
            ticks: {
              font: { size: 11 },
              callback: v => '₡' + Number(v).toLocaleString('es-CR')
            },
            grid: { color: 'rgba(0,0,0,0.06)' }
          },
          y: {
            ticks: { font: { size: 11 } },
            grid: { display: false }
          }
        }
      }
    });
  }

cargarActividadesProximas(): void {
  this.dashboardService.obtenerDashboardActividadesProximas().subscribe(data => {
    this.datosActividades = data;
    this.buildActividadesChart(data);
  });
}

private buildActividadesChart(data: DashboardActividadesProximas[]): void {
  this.actividadesChart?.destroy();

  this.actividadesChart = new Chart(this.actividadesCanvas.nativeElement, {
    type: 'bar',
    data: {
      labels: data.map(d => d.nombreActividad),
      datasets: [
        {
          label: 'Cupo total',
          data: data.map(d => d.cupoDisponible),
          backgroundColor: 'rgba(50, 102, 173, 0.18)',
          borderRadius: 4,
          borderWidth: 0
        },
        {
          label: 'Inscritos',
          data: data.map(d => d.totalInscritos),
          backgroundColor: '#3266ad',
          borderRadius: 4,
          borderWidth: 0
        }
      ]
    },
    options: {
      responsive:          true,
      maintainAspectRatio: false,
      plugins: { legend: { display: false } },
      scales: {
        x: {
          ticks: {
            font: { size: 10 },
            autoSkip: false,
            // Acortar nombres largos en el eje
            callback: function(val, index) {
              const label = data[index].nombreActividad;
              return label.length > 12 ? label.substring(0, 12) + '…' : label;
            }
          },
          grid: { display: false }
        },
        y: {
          beginAtZero: true,
          ticks: { font: { size: 11 }, stepSize: 1 },
          grid: { color: 'rgba(0,0,0,0.06)' }
        }
      }
    }
  });
}

}