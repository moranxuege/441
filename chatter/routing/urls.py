from django.contrib import admin
from django.urls import path
from app import views

urlpatterns = [
    path('getchatts/', views.getchatts, name='getchatts'),
    path('postchatt/', views.postchatt, name='postchatt'),
    path('admin/', admin.site.urls),
]
