AUI().ready(
	'liferay-sign-in-modal',
	function(A) {
		var signIn = A.all('.sign-in > a');

		if (signIn && signIn.getData('redirect') !== 'true') {
			signIn.plug(Liferay.SignInModal);
		}
	}
)

var fixPaginationButtons = function() {

	$('.lfr-ddm-form-pagination-prev').text('BACK');
	$('.lfr-ddm-form-pagination-next').text('NEXT');

	$('.lfr-ddm-form-pagination-prev').removeClass('btn-primary').addClass('btn-secondary');

}


Liferay.on(
	'ddmFormPageShow',

	function () {
		fixPaginationButtons();
	}

);


Liferay.on(
	'allPortletsReady',

	function () {

		fixPaginationButtons();
		
		$('#content').animate({ opacity: 1 }, 300);

		///THERE SHOULD BE SOME OTHER EVENT, BUT ANYWAY
		if ($('.ddm-form-success-page')) {

			$('.ddm-form-name').addClass('done-icon-left');

			$('.ddm-form-description').css({ marginBottom: '45px' })
				.after('<a class="btn btn-primary" href="' + home_url + '">Go back to Home</a>');

		}

	}
);
