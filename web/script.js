
const query_name = document.getElementById("query_name");
const query_n = document.getElementById("query_n");
const query_area_size = document.getElementById("query_area_size");

const misc_area_size = document.getElementById("misc_area_size");
const misc_name = document.getElementById("misc_name");

const world_pop = document.getElementById("world_pop")

console.log("Script loaded!");

function send_query(q, as, e) {
    fetch("http://localhost/api", {
        q: q,
        as: as,
        e: e,
    }).then((response) => {
        let json = JSON.parse(response.body.toString());

        if (q == "m0") { // Misc query

        } else if (q == "p0") { //

        } else {

        }

    })
}

function run_query() {
    send_query(
        query_name.value,
        query_n.value,
        query_area_size.value,
    )
}

function run_misc() {
    send_query(
        "m0",
        misc_area_size.value,
        misc_name.value
    )
}

function world_population() {
    world_pop.textContent = "idk lol"
}