$(document).ready(function(){
    getMonthGraph();
    getWeekGraph();
});

function getWeekGraph() {
    $.ajax({
        url: '/api/week',
        type: 'GET',
        success: function(response) {
            WeekChart(response);
            console.log('response:', response);

        },
        error: function(xhr, status, error) {
            console.log('Error:', error);
        }
    });
}


function getMonthGraph() {
    $.ajax({
        url: '/api/month',
        type: 'GET',
        success: function(response) {
            MonthChart(response);
        },
        error: function(xhr, status, error) {
            console.log('Error:', error);
        }
    });
}

function WeekChart(chartData) {
    new Chart(document.getElementById("pie-chart"), {
        type: 'pie',
        data: {
            labels: chartData.map(function (chart) {
                return chart.day;
            }),
            datasets: [{
                label: "Population (millions)",
                backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#a22841","#f31530"],
                data: chartData.map(function (chart) {
                    return chart.totalPrice;
                }),
            }]
        },
        options: {
            title: {
                display: true,
            },
            legend: {
                display: true,
            }
        }
    });
}

function MonthChart(chartData) {
    var ctx = document.getElementById('bar-chart').getContext('2d');
    var chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: chartData.map(function (chart) {
                return chart.month + '월';
            }),
            datasets: [{
                label: '월별 매출',
                data: chartData.map(function (chart) {
                    return chart.totalPrice;
                }),
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: false,
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });
}