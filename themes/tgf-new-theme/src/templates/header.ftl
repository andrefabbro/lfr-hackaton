		<header id="banner">

			<div class="tgf-navbar navbar navbar-classic navbar-top py-3 <#if navbar_is_solid> solid</#if>">
				<div class="container user-personal-bar">
					<div class="align-items-center autofit-row">
						
						<a class="navbar-brand d-flex align-items-center mr-3" href="${home_url}">
							<img src="${site_logo}" width="101" class="mr-2" alt="">
						</a>

						<div class="autofit-col autofit-col-expand">
							
							<#if show_header_navigation>
								<@liferay.navigation_menu />
							</#if>

						</div>

						<div class="autofit-col">
							<@liferay.user_personal_bar />

						</div>
					</div>
				</div>
			</div>

		</header>