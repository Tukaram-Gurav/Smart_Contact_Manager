const toggleSidebar = () => {
	if ($(".sidebar").is(":visible")) {
		$(".sidebar").css("display", "none");
		$(".content").css("margin-left", "0%");
	}
	else {
		$(".sidebar").css("display", "block");
		$(".content").css("margin-left", "20%");
	}
}


///Search Contacts
const search = () => {

	let query = $("#search-input").val();

	if (query == "") {
		$(".search-result").hide();
	}
	else {
		let url = `http://localhost:8080/search/${query}`;

		fetch(url).then((response) => {
			return response.json();
		}).then((data) => {
			console.log(data);

			let text = `<div class='list-group'>`;
			data.forEach((contact) => {
				text += `<a  class='list-group-item list-group-item-action' href='/user/${contact.cid}/contact' >${contact.name}</a>`

			});
			text += `</div>`;
			$(".search-result").html(text);
			$(".search-result").show();
		});

	}
};


