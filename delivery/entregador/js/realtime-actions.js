
function init() {
    registerHandler();
};

function registerHandler() {
    var eventBus = new EventBus('http://localhost:8082/positions');
    eventBus.onopen = function () {
        eventBus.registerHandler('newPosition', function (error, message) {
            document.getElementById('positions').value +=message.body+'\n\n----------------\n\n';
        });
    }
};

