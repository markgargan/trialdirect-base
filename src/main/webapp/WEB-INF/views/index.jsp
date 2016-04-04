<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="trialdirect">
<head>
    <meta charset="utf-8">
    <title>Trial Direct</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="language" content="en">
    <meta name="viewport" content="initial-scale=1">
    <meta name="robots" content="noindex, nofollow">
    <meta ng-if="!$state.includes('logout')" name="${_csrf.parameterName}" content="${_csrf.token}"/>

    <!-- <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Open+Sans:400,700,800"> -->
    <link rel="stylesheet" type="text/css" href="../assets/font-awesome-4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="../assets/css/base.css">

    <link rel="stylesheet" href="../assets/css/bootstrap.2.3.1.min.css">
    <link rel="stylesheet" href="../bower_components/bootstrap-css-only/css/bootstrap.css"/>
    <link rel="stylesheet" href="../assets/css/style.css"/>

    <style type="text/css">@charset "UTF-8";
    [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak, .ng-hide:not(.ng-hide-animate) {
        display: none !important;
    }

    ng\:form {
        display: block;
    }

    .ng-animate-shim {
        visibility: hidden;
    }

    .ng-anchor {
        position: absolute;
    }</style>

</head>


<!-- ngInclude: 'assets/partials/navAdmin.html' -->
<div data-ng-include="'assets/partials/navAdmin.html'"></div>

<div data-ng-include="'assets/partials/header.html'"></div>

<body data-pinterest-extension-installed="cr1.39.1">

<a href="#top" style="display: none;"><i class="fa fa-angle-double-up fa-2x"></i><span>Back To Top</span></a>

<main class="trial-direct">
    <div class="limiter">
        <div class="view-container">

        <div ng-if="!$state.current.views.hasOwnProperty('viewB')" class="col-xs-12"
             ui-view="viewA"></div>

        <div ng-if="$state.current.views.hasOwnProperty('viewB')" class="col-xs-6" ui-view="viewA"></div>
        <div ng-if="$state.current.views.hasOwnProperty('viewB')" class="col-xs-6" ui-view="viewB"></div>

        </div>
    </div>
</main>

<!--                                                      -->
    <!-- Uncomment to be able to follow the current state     -->
    <!--                                                      -->
    <!--<pre>-->
    <!--&lt;!&ndash; Here's some values to keep an eye on in the sample in order to understand $state and $stateParams &ndash;&gt;-->
    <!--$state = {{$state.$current.name}}-->
    <!--$state full url = {{ $state.$current.url.source }}-->
    <!--$stateParams = {{$stateParams}}-->
    <!--Use full screen = {{$state.current.views.hasOwnProperty('viewB')}}-->
    <!--&lt;!&ndash; $state.$current is not a public api, we are using it to-->
    <!--display the full url for learning purposes&ndash;&gt;-->
    <!--</pre>-->
</div>


<!-- ngInclude: 'assets/partials/footer.html' -->
<div data-ng-include="'assets/partials/footer.html'"/>

<script type="text/javascript" src="../bower_components/angular/angular.js"></script>
<script type="text/javascript" src="../bower_components/jquery/dist/jquery.js"></script>
<script type="text/javascript" src="../bower_components/angular-animate/angular-animate.js"></script>
<script type="text/javascript" src="../bower_components/angular-resource/angular-resource.js"></script>
<script type="text/javascript" src="../bower_components/angular-ui-router/release/angular-ui-router.js"></script>
<script type="text/javascript"
        src="../bower_components/angular-spring-data-rest/dist/angular-spring-data-rest.js"></script>
<script type="text/javascript" src="../bower_components/lodash/dist/lodash.js"></script>

<script type="text/javascript" src="../app/app.js"></script>

<script type="text/javascript" src="../app/common/services/QuestionService.js"></script>
<script type="text/javascript" src="../app/common/services/AnswerService.js"></script>
<script type="text/javascript" src="../app/common/services/QuestionnaireEntryResourceService.js"></script>


<!-- TherapeuticAreas start -->
<script type="text/javascript" src="../app/therapeuticareas/controllers/TherapeuticAreaController.js"></script>
<script type="text/javascript" src="../app/therapeuticareas/controllers/TherapeuticAreaEditController.js"></script>
<script type="text/javascript" src="../app/therapeuticareas/states/TherapeuticAreaStateManager.js"></script>
<script type="text/javascript" src="../app/therapeuticareas/services/TherapeuticAreaResourceService.js"></script>
<!-- TherapeuticAreas end -->

<!-- Trials start -->
<script type="text/javascript" src="../app/trials/controllers/TrialController.js"></script>
<script type="text/javascript" src="../app/trials/controllers/TrialEditController.js"></script>
<script type="text/javascript" src="../app/trials/states/TrialStateManager.js"></script>
<script type="text/javascript" src="../app/trials/services/TrialResourceService.js"></script>
<script type="text/javascript" src="../app/trials/services/TrialService.js"></script>
<script type="text/javascript" src="../app/trials/services/TrialSelectorQuestionnaireEntryResourceService.js"></script>
<!-- Trials end -->


<!--&lt;!&ndash; Users start &ndash;&gt;-->
<script type="text/javascript" src="../app/users/controllers/UserController.js"></script>
<script type="text/javascript" src="../app/users/controllers/UserEditController.js"></script>
<script type="text/javascript" src="../app/users/states/UserStateManager.js"></script>
<script type="text/javascript" src="../app/users/services/UserResourceService.js"></script>
<script type="text/javascript" src="../app/users/services/UserSelectorQuestionnaireEntryResourceService.js"></script>
<!-- Users end -->

</body>

</html>